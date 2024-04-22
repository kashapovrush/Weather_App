package com.kashapovrush.favourite_screen.favourite

import com.kashapovrush.utils.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun noClickSearch()

    fun onClickAddToFavourite()

    fun onCityItemClick(city: City)
}