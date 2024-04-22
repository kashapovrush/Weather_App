package com.kashapovrush.weatherapptest.presentation.favourite

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.usecase.FavouriteCitiesUseCase
import com.kashapovrush.weatherapptest.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavouriteStore :
    Store<FavouriteStore.Intent, FavouriteStore.State, FavouriteStore.Label> {

    sealed interface Intent {

        data object ClickFavourite : Intent

        data object ClickSearch : Intent

        data class ClickCityItem(val city: City) : Intent
    }

    data class State(val cityItems: List<CityItem>) {

        data class CityItem(val city: City, val weatherState: WeatherState)

        sealed interface WeatherState {

            data object Initial : WeatherState

            data object Loading : WeatherState

            data object Error : WeatherState

            data class Loaded(val temp: Float, val icon: String): WeatherState

        }
    }

    sealed interface Label {

        data object ClickFavourite : Label

        data object ClickSearch : Label

        data class ClickCityItem(val city: City) : Label
    }
}

class FavouriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val favouriteCitiesUseCase: FavouriteCitiesUseCase
) {

    fun create(): FavouriteStore = object : FavouriteStore,
        Store<FavouriteStore.Intent, FavouriteStore.State, FavouriteStore.Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = FavouriteStore.State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl
        ) {}

    sealed interface Action {

        data class FavouriteCitiesLoaded(val cities: List<City>) : Action
    }

    sealed interface Msg {

        data class FavouriteCitiesLoaded(val cities: List<City>) : Msg

        data class WeatherLoaded(val cityId: Int, val temp: Float, val conditionUrl: String) : Msg

        data class WeatherLoadingError(val cityId: Int) : Msg

        data class WeatherIsLoading(val cityId: Int) : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                favouriteCitiesUseCase().collect {
                    dispatch(Action.FavouriteCitiesLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<FavouriteStore.Intent, Action, FavouriteStore.State, Msg, FavouriteStore.Label>() {

        override fun executeAction(action: Action, getState: () -> FavouriteStore.State) {
            super.executeAction(action, getState)
            when (action) {
                is Action.FavouriteCitiesLoaded -> {
                    val cities = action.cities
                    dispatch(Msg.FavouriteCitiesLoaded(cities))
                    cities.forEach {
                        scope.launch {
                            loadWeatherForCity(it)
                        }
                    }
                }
            }
        }

        private suspend fun loadWeatherForCity(city: City) {
            dispatch(Msg.WeatherIsLoading(city.id))
            try {
                val weather = getWeatherUseCase(city.id)
                dispatch(
                    Msg.WeatherLoaded(
                        cityId = city.id,
                        temp = weather.temperature,
                        conditionUrl = weather.conditionUrl
                    )
                )
            } catch (e: Exception) {
                Msg.WeatherLoadingError(city.id)
            }

        }

        override fun executeIntent(
            intent: FavouriteStore.Intent,
            getState: () -> FavouriteStore.State
        ) {
            super.executeIntent(intent, getState)
            when (intent) {
                is FavouriteStore.Intent.ClickCityItem -> {
                    publish(FavouriteStore.Label.ClickCityItem(intent.city))
                }

                FavouriteStore.Intent.ClickFavourite -> {
                    publish(FavouriteStore.Label.ClickFavourite)
                }

                FavouriteStore.Intent.ClickSearch -> {
                    publish(FavouriteStore.Label.ClickSearch)
                }

            }
        }
    }

    private object ReducerImpl : Reducer<FavouriteStore.State, Msg> {

        override fun FavouriteStore.State.reduce(msg: Msg): FavouriteStore.State = when (msg) {
            is Msg.FavouriteCitiesLoaded -> {
                copy(
                    cityItems = msg.cities.map {
                        FavouriteStore.State.CityItem(
                            city = it,
                            weatherState = FavouriteStore.State.WeatherState.Initial
                        )
                    }
                )
            }

            is Msg.WeatherIsLoading -> {
                copy(
                    cityItems = cityItems.map {
                        if (it.city.id == msg.cityId) {
                            it.copy(weatherState = FavouriteStore.State.WeatherState.Loading)
                        } else {
                            it
                        }
                    }
                )
            }

            is Msg.WeatherLoaded -> {
                copy(
                    cityItems = cityItems.map {
                        if (it.city.id == msg.cityId) {
                            it.copy(
                                weatherState = FavouriteStore.State.WeatherState.Loaded(
                                    temp = msg.temp,
                                    icon = msg.conditionUrl
                                )
                            )
                        } else {
                            it
                        }
                    }
                )
            }

            is Msg.WeatherLoadingError -> {
                copy(
                    cityItems = cityItems.map {
                        if (it.city.id == msg.cityId) {
                            it.copy(weatherState = FavouriteStore.State.WeatherState.Error)
                        } else {
                            it
                        }
                    }
                )
            }

        }

    }
}