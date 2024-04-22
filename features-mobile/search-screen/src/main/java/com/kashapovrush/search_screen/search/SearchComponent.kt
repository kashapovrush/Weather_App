package com.kashapovrush.search_screen.search

import com.kashapovrush.utils.entity.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun onClickBack()

    fun onClickSearch()

    fun changeSearchQuery(query: String)

    fun onClickCityItem(city: City)
}