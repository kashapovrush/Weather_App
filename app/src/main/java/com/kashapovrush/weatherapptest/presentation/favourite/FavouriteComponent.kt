package com.kashapovrush.weatherapptest.presentation.favourite

import com.kashapovrush.weatherapptest.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun noClickSearch()

    fun onClickAddToFavourite()

    fun onCityItemClick(city: City)
}