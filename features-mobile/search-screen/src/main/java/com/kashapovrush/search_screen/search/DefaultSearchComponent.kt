package com.kashapovrush.search_screen.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kashapovrush.common.extensions.componentScope
import com.kashapovrush.utils.entity.City
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSearchComponent @AssistedInject constructor(
    private val factory: SearchStoreFactory,
    @Assisted("searchReason") private val searchReason: SearchReason,
    @Assisted("onClickBack") private val onBackClicked: () -> Unit,
    @Assisted("openForecast") private val openForecast: (City) -> Unit,
    @Assisted("saveToFavourite") private val saveToFavourite: () -> Unit,
    @Assisted("componentContext") private val componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { factory.create(searchReason) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    SearchStore.Label.ClickBack -> onBackClicked()
                    is SearchStore.Label.OpenForecast -> openForecast(it.city)
                    SearchStore.Label.SaveToFavourite -> saveToFavourite()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State> = store.stateFlow

    override fun onClickBack() {
        store.accept(SearchStore.Intent.ClickBack)
    }

    override fun onClickSearch() {
        store.accept(SearchStore.Intent.ClickSearch)
    }

    override fun changeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangedSearchQuery(query))
    }

    override fun onClickCityItem(city: City) {
        store.accept(SearchStore.Intent.ClickCity(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("searchReason") searchReason: SearchReason,
            @Assisted("onClickBack") onClickBack: () -> Unit,
            @Assisted("openForecast") openForecast: (City) -> Unit,
            @Assisted("saveToFavourite") saveToFavourite: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSearchComponent
    }

}