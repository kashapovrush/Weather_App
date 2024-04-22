package com.kashapovrush.weatherapptest.domain.usecase

import com.kashapovrush.weatherapptest.domain.repository.FavouriteRepository
import javax.inject.Inject

class FavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke() = repository.favouriteCities
}