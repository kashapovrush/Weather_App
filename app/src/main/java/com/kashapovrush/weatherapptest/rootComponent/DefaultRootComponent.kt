package com.kashapovrush.weatherapptest.rootComponent

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.kashapovrush.details_screen.details.DefaultDetailsComponent
import com.kashapovrush.favourite_screen.favourite.DefaultFavouriteComponent
import com.kashapovrush.search_screen.search.DefaultSearchComponent
import com.kashapovrush.search_screen.search.SearchReason
import com.kashapovrush.utils.entity.City
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when(config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onClickBack = { navigation.pop()},
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }
            Config.Favourite -> {
                val component = favouriteComponentFactory.create(
                    onClickCityItem = { navigation.push(Config.Details(it))},
                    onClickFavourite = { navigation.push(Config.Search(SearchReason.AddToFavourite))},
                    onClickSearch = {navigation.push(Config.Search(SearchReason.RegularSearch))},
                    componentContext = componentContext
                )
                RootComponent.Child.Favourite(component)
            }
            is Config.Search -> {
                val component = searchComponentFactory.create(
                    onClickBack = { navigation.pop()},
                    openForecast = { navigation.push(Config.Details(it))},
                    saveToFavourite = { navigation.pop()},
                    componentContext = componentContext,
                    searchReason = config.searchReason
                )

                RootComponent.Child.Search(component)
            }
        }
    }

    sealed interface Config: Parcelable {

        @Parcelize
        data object Favourite: Config

        @Parcelize
        data class Search(val searchReason: SearchReason):
            Config

        @Parcelize
        data class Details(val city: City): Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}