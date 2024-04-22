package com.kashapovrush.weatherapptest.data.repository

import com.kashapovrush.weatherapptest.data.mapper.toEntities
import com.kashapovrush.weatherapptest.data.network.api.ApiService
import com.kashapovrush.weatherapptest.domain.entity.City
import com.kashapovrush.weatherapptest.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}