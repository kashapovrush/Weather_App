package com.kashapovrush.database_impl.mapper

import com.kashapovrush.database_impl.database.CityDb
import com.kashapovrush.utils.entity.City

fun CityDb.toEntity(): City = City(id, name, country)

fun List<CityDb>.toEntities(): List<City> = map {
    it.toEntity()
}

fun City.toDbModel(): CityDb =
    CityDb(id, name, country)