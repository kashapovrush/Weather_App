package com.kashapovrush.database_impl.repository


import com.kashapovrush.utils.entity.City
import com.kashapovrush.database_api.repository.FavouriteRepository
import com.kashapovrush.database_impl.database.CityDao
import com.kashapovrush.database_impl.mapper.toDbModel
import com.kashapovrush.database_impl.mapper.toEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : FavouriteRepository {

    override val favouriteCities: Flow<List<com.kashapovrush.utils.entity.City>> = cityDao.getFavouriteCities().map {
        it.toEntities()
    }

    override suspend fun addToFavourite(city: com.kashapovrush.utils.entity.City) {
        cityDao.addFavouriteCity(city.toDbModel())
    }

    override suspend fun removeFromFavourite(cityId: Int) {
        cityDao.removeFavouriteCity(cityId)
    }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> {
        return cityDao.observeAsFavourite(cityId)
    }
}