package com.kashapovrush.weatherapptest.rootComponent

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.kashapovrush.details_screen.details.DetailsContent
import com.kashapovrush.favourite_screen.favourite.FavouriteContent
import com.kashapovrush.search_screen.search.SearchContent
import com.kashapovrush.palette.theme.WeatherAppTestTheme

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