package com.kashapovrush.weatherapptest.presentation.search

import com.kashapovrush.weatherapptest.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun onClickBack()

    fun onClickSearch()

    fun changeSearchQuery(query: String)

    fun onClickCityItem(city: City)
}