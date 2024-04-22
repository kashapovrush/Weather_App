package com.kashapovrush.weatherapptest.data.mapper

import com.kashapovrush.weatherapptest.data.database.CityDb
import com.kashapovrush.weatherapptest.domain.entity.City

fun CityDb.toEntity(): City = City(id, name, country)

fun List<CityDb>.toEntities(): List<City> = map {
    it.toEntity()
}

fun City.toDbModel(): CityDb = CityDb(id, name, country)