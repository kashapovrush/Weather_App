package com.kashapovrush.weatherapptest.data.mapper

import com.kashapovrush.weatherapptest.data.network.dto.CityDto
import com.kashapovrush.weatherapptest.domain.entity.City

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDto>.toEntities(): List<City> = map {
    it.toEntity()
}