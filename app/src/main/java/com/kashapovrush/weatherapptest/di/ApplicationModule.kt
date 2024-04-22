package com.kashapovrush.weatherapptest.di

import android.content.Context
import com.kashapovrush.weatherapptest.data.database.AppDatabase
import com.kashapovrush.weatherapptest.data.database.CityDao
import com.kashapovrush.weatherapptest.data.network.api.ApiFactory
import com.kashapovrush.weatherapptest.data.network.api.ApiService
import com.kashapovrush.weatherapptest.data.repository.FavouriteRepositoryImpl
import com.kashapovrush.weatherapptest.data.repository.SearchRepositoryImpl
import com.kashapovrush.weatherapptest.data.repository.WeatherRepositoryImpl
import com.kashapovrush.weatherapptest.domain.repository.FavouriteRepository
import com.kashapovrush.weatherapptest.domain.repository.SearchRepository
import com.kashapovrush.weatherapptest.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface ApplicationModule {

    @[Binds ApplicationScope]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[Binds ApplicationScope]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @[Binds ApplicationScope]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @[Provides ApplicationScope]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[Provides ApplicationScope]
        fun provideAppDatabase(context: Context): AppDatabase=  AppDatabase.getInstance(context)

        @[Provides ApplicationScope]
        fun provideCityDao(database: AppDatabase): CityDao = database.cityDao()
    }
}