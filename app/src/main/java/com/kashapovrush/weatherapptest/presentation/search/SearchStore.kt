package com.kashapovrush.weatherapptest.presentation.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.usecase.ChangeFavouriteStateUseCase
import com.kashapovrush.weatherapptest.domain.usecase.SearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<SearchStore.Intent, SearchStore.State, SearchStore.Label> {

    sealed interface Intent {

        data class ChangedSearchQuery(val query: String) : Intent

        data object ClickBack : Intent

        data object ClickSearch : Intent

        data class ClickCity(val city: City) : Intent


    }

    data class State(
        val searchQuery: String,
        val searchState: SearchState
    ) {

        sealed interface SearchState {

            data object Initial : SearchState

            data object Loading : SearchState

            data object Error : SearchState

            data object EmptyResult : SearchState

            data class SuccessLoaded(val cities: List<City>) : SearchState
        }

    }

    sealed interface Label {

        data object ClickBack : Label

        data object SaveToFavourite : Label

        data class OpenForecast(val city: City) : Label

    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchUseCase: SearchUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase
) {

    fun create(searchReason: SearchReason): SearchStore = object : SearchStore,
        Store<SearchStore.Intent, SearchStore.State, SearchStore.Label> by storeFactory.create(
            name = "SearchStore",
            initialState = SearchStore.State(
                searchQuery = "",
                searchState = SearchStore.State.SearchState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(searchReason) },
            reducer = ReducerImpl
        ) {}

    sealed interface Action

    sealed interface Msg {

        data object LoadingSearchResult : Msg

        data class ChangeSearchQuery(val query: String) : Msg

        data class SearchResultLoaded(val cities: List<City>) : Msg

        data object SearchResultError : Msg

    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {

        }
    }

    private inner class ExecutorImpl(val openReason: SearchReason) :
        CoroutineExecutor<SearchStore.Intent, Action, SearchStore.State, Msg, SearchStore.Label>() {

        private var searchJob: Job? = null

        override fun executeIntent(intent: SearchStore.Intent, getState: () -> SearchStore.State) {
            super.executeIntent(intent, getState)
            when (intent) {
                is SearchStore.Intent.ChangedSearchQuery -> dispatch(Msg.ChangeSearchQuery(intent.query))

                SearchStore.Intent.ClickBack -> publish(SearchStore.Label.ClickBack)

                is SearchStore.Intent.ClickCity -> {
                    when (openReason) {
                        SearchReason.AddToFavourite -> {
                            scope.launch {
                                changeFavouriteStateUseCase.addToFavourite(intent.city)
                                publish(SearchStore.Label.SaveToFavourite)
                            }
                        }

                        SearchReason.RegularSearch -> {
                            publish(SearchStore.Label.OpenForecast(intent.city))
                        }
                    }
                }

                SearchStore.Intent.ClickSearch -> {
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        dispatch(Msg.LoadingSearchResult)
                        try {
                            val cities = searchUseCase(getState().searchQuery)
                            dispatch(Msg.SearchResultLoaded(cities))
                        } catch (e: Exception) {
                            dispatch(Msg.SearchResultError)
                        }

                    }

                }
            }
        }
    }

    private object ReducerImpl : Reducer<SearchStore.State, Msg> {
        override fun SearchStore.State.reduce(msg: Msg): SearchStore.State = when (msg) {
            is Msg.ChangeSearchQuery -> copy(searchQuery = msg.query)
            Msg.LoadingSearchResult -> copy(searchState = SearchStore.State.SearchState.Loading)
            Msg.SearchResultError -> copy(searchState = SearchStore.State.SearchState.Error)
            is Msg.SearchResultLoaded -> {
                if (msg.cities.isNotEmpty()) {
                    copy(searchState = SearchStore.State.SearchState.SuccessLoaded(msg.cities))
                } else {
                    copy(searchState = SearchStore.State.SearchState.EmptyResult)
                }
            }
        }

    }
}