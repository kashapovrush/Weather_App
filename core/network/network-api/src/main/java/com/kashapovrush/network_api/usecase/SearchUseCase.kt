package com.kashapovrush.network_api.usecase

import com.kashapovrush.network_api.repository.SearchRepository
import com.kashapovrush.utils.entity.City
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String): List<City> = repository.search(query)
}