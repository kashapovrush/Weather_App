package com.kashapovrush.network_impl.mapper

import com.kashapovrush.network_impl.dto.CityDto
import com.kashapovrush.utils.entity.City

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDto>.toEntities(): List<City> = map {
    it.toEntity()
}