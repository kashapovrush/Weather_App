package com.kashapovrush.network_impl.repository

import com.kashapovrush.network_api.repository.SearchRepository
import com.kashapovrush.network_impl.api.ApiService
import com.kashapovrush.network_impl.mapper.toEntities
import com.kashapovrush.utils.entity.City
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}