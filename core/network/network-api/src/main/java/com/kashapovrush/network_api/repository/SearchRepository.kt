package com.kashapovrush.network_api.repository


import com.kashapovrush.utils.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}