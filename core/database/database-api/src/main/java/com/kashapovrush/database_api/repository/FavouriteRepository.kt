package com.kashapovrush.database_api.repository

import com.kashapovrush.utils.entity.City
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteCities: Flow<List<City>>

    suspend fun addToFavourite(city: City)

    suspend fun removeFromFavourite(cityId: Int)

    fun observeIsFavourite(cityId: Int): Flow<Boolean>
}