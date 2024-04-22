package com.kashapovrush.weatherapptest.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kashapovrush.weatherapptest.presentation.details.DefaultDetailsComponent
import com.kashapovrush.weatherapptest.presentation.favourite.DefaultFavouriteComponent
import com.kashapovrush.weatherapptest.presentation.search.DefaultSearchComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Favourite(val component: DefaultFavouriteComponent): Child

        data class Search(val component: DefaultSearchComponent): Child

        data class Details(val component: DefaultDetailsComponent): Child
    }
}