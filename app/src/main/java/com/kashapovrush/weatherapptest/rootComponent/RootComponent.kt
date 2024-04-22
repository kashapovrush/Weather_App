package com.kashapovrush.weatherapptest.rootComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kashapovrush.details_screen.details.DefaultDetailsComponent
import com.kashapovrush.favourite_screen.favourite.DefaultFavouriteComponent
import com.kashapovrush.search_screen.search.DefaultSearchComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Favourite(val component: DefaultFavouriteComponent) : Child

        data class Search(val component: DefaultSearchComponent) : Child

        data class Details(val component: DefaultDetailsComponent) : Child
    }
}