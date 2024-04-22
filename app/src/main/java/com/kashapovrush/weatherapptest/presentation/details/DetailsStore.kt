package com.kashapovrush.weatherapptest.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.entity.Forecast
import com.kashapovrush.weatherapptest.domain.usecase.ChangeFavouriteStateUseCase
import com.kashapovrush.weatherapptest.domain.usecase.GetForecastUseCase
import com.kashapovrush.weatherapptest.domain.usecase.ObserveIsFavouriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailsStore : Store<DetailsStore.Intent, DetailsStore.State, DetailsStore.Label> {


    sealed interface Intent {

        data object ClickBack : Intent

        data object ClickChangeFavouriteStatus : Intent
    }

    data class State(
        val city: City,
        val isFavourite: Boolean,
        val forecastState: ForecastState
    ) {

        sealed interface ForecastState {

            data object Initial : ForecastState

            data object Loading : ForecastState

            data object Error : ForecastState

            data class Loaded(val forecast: Forecast): ForecastState
        }
    }

    sealed interface Label {

        data object ClickBack : Label
    }
}

class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeIsFavouriteUseCase: ObserveIsFavouriteUseCase,
    private val getForecastUseCase: GetForecastUseCase
) {

    fun create(city: City): DetailsStore = object : DetailsStore,
        Store<DetailsStore.Intent, DetailsStore.State, DetailsStore.Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = DetailsStore.State(
                city = city,
                isFavourite = false,
                forecastState = DetailsStore.State.ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed interface Action {

        data class FavouriteStatusChanged(val isFavourite: Boolean) : Action

        data object ForecastStartLoading : Action

        data class ForecastLoaded(val forecast: Forecast) : Action

        data object ForecastError : Action

    }

    sealed interface Msg {

        data class FavouriteStatusChanged(val isFavourite: Boolean) : Msg

        data object ForecastStartLoading : Msg

        data class ForecastLoaded(val forecast: Forecast) : Msg

        data object ForecastError : Msg

    }

    private inner class BootstrapperImpl(private val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeIsFavouriteUseCase.invoke(city.id).collect {
                    dispatch(Action.FavouriteStatusChanged(it))
                }
            }

            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastError)
                }

            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<DetailsStore.Intent, Action, DetailsStore.State, Msg, DetailsStore.Label>() {

        override fun executeAction(action: Action, getState: () -> DetailsStore.State) {
            super.executeAction(action, getState)

            when(action) {
                is Action.FavouriteStatusChanged -> dispatch(Msg.FavouriteStatusChanged(action.isFavourite))
                is Action.ForecastError -> dispatch(Msg.ForecastError)
                is Action.ForecastLoaded -> dispatch(Msg.ForecastLoaded(action.forecast))
                is Action.ForecastStartLoading -> dispatch(Msg.ForecastStartLoading)
            }
        }

        override fun executeIntent(
            intent: DetailsStore.Intent,
            getState: () -> DetailsStore.State
        ) {
            super.executeIntent(intent, getState)
            when (intent) {
                DetailsStore.Intent.ClickBack -> {
                    publish(DetailsStore.Label.ClickBack)
                }

                DetailsStore.Intent.ClickChangeFavouriteStatus -> {
                    scope.launch {
                        val state = getState()
                        if (state.isFavourite) {
                            changeFavouriteStateUseCase.removeFromFavourite(state.city.id)
                        } else {
                            changeFavouriteStateUseCase.addToFavourite(state.city)
                        }
                    }

                }
            }
        }
    }

    private object ReducerImpl : Reducer<DetailsStore.State, Msg> {
        override fun DetailsStore.State.reduce(msg: Msg): DetailsStore.State = when(msg) {

            is Msg.FavouriteStatusChanged -> {
                copy(isFavourite = msg.isFavourite)
            }
            Msg.ForecastError -> {
                copy(forecastState = DetailsStore.State.ForecastState.Error)
            }
            is Msg.ForecastLoaded -> {
                copy(forecastState = DetailsStore.State.ForecastState.Loaded(msg.forecast))
            }
            Msg.ForecastStartLoading -> {
                copy(forecastState = DetailsStore.State.ForecastState.Loading)
            }
        }

    }

}

