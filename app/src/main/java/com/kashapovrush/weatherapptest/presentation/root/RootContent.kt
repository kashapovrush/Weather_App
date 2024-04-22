package com.kashapovrush.weatherapptest.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.kashapovrush.weatherapptest.presentation.details.DetailsContent
import com.kashapovrush.weatherapptest.presentation.favourite.FavouriteContent
import com.kashapovrush.weatherapptest.presentation.search.SearchContent
import com.kashapovrush.weatherapptest.presentation.theme.WeatherAppTestTheme

@Composable
fun RootContent(component: RootComponent) {

    WeatherAppTestTheme {
        Children(stack = component.stack) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }

                is RootComponent.Child.Favourite -> {
                    FavouriteContent(component = instance.component)
                }

                is RootComponent.Child.Search -> {
                    SearchContent(component = instance.component)
                }
            }
        }

    }

}