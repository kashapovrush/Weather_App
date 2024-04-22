package com.kashapovrush.weatherapptest.data.repository

import com.kashapovrush.weatherapptest.data.database.CityDao
import com.kashapovrush.weatherapptest.data.mapper.toDbModel
import com.kashapovrush.weatherapptest.data.mapper.toEntities
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : FavouriteRepository {

    override val favouriteCities: Flow<List<City>> = cityDao.getFavouriteCities().map {
        it.toEntities()
    }

    override suspend fun addToFavourite(city: City) {
        cityDao.addFavouriteCity(city.toDbModel())
    }

    override suspend fun removeFromFavourite(cityId: Int) {
        cityDao.removeFavouriteCity(cityId)
    }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> {
        return cityDao.observeAsFavourite(cityId)
    }
}