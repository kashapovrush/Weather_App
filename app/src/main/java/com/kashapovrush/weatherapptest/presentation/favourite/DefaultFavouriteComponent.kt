package com.kashapovrush.weatherapptest.presentation.favourite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.presentation.extensions.componentScope
import dagger.Component
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultFavouriteComponent @AssistedInject constructor(
    private val factory: FavouriteStoreFactory,
    @Assisted("onClickCityItem") private val onClickCityItem: (City) -> Unit,
    @Assisted("onClickFavourite") private val onClickFavourite: () -> Unit,
    @Assisted("onClickSearch") private val onClickSearch: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : FavouriteComponent, ComponentContext by componentContext {



    private val store = instanceKeeper.getStore { factory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is FavouriteStore.Label.ClickCityItem -> onClickCityItem(it.city)
                    FavouriteStore.Label.ClickFavourite -> onClickFavourite()
                    FavouriteStore.Label.ClickSearch -> onClickSearch()
                }
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State> = store.stateFlow

    override fun noClickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun onClickAddToFavourite() {
        store.accept(FavouriteStore.Intent.ClickFavourite)
    }

    override fun onCityItemClick(city: City) {
        store.accept(FavouriteStore.Intent.ClickCityItem(city))
    }


    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onClickCityItem") onClickCityItem: (City) -> Unit,
            @Assisted("onClickFavourite") onClickFavourite: () -> Unit,
            @Assisted("onClickSearch") onClickSearch: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultFavouriteComponent
    }
}