package com.kashapovrush.database_api.usecase

import com.kashapovrush.database_api.repository.FavouriteRepository
import javax.inject.Inject

class ObserveIsFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}