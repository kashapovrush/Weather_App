package com.kashapovrush.weatherapptest.domain.usecase

import com.kashapovrush.weatherapptest.domain.repository.FavouriteRepository
import javax.inject.Inject

class ObserveIsFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}