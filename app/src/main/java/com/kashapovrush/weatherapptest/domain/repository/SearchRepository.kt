package com.kashapovrush.weatherapptest.domain.repository

import com.kashapovrush.weatherapptest.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}