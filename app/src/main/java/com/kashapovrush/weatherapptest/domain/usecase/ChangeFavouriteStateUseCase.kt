package com.kashapovrush.weatherapptest.domain.usecase

import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend fun addToFavourite(city: City) = repository.addToFavourite(city)

    suspend fun removeFromFavourite(cityId: Int) = repository.removeFromFavourite(cityId)

}