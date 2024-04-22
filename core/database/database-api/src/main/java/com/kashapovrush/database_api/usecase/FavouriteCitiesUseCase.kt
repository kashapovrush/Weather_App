package com.kashapovrush.database_api.usecase

import com.kashapovrush.database_api.repository.FavouriteRepository
import javax.inject.Inject

class FavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke() = repository.favouriteCities
}