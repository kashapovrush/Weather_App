package com.kashapovrush.weatherapptest.di

import android.content.Context
import com.kashapovrush.database_api.repository.FavouriteRepository
import com.kashapovrush.database_impl.database.AppDatabase
import com.kashapovrush.database_impl.database.CityDao
import com.kashapovrush.database_impl.repository.FavouriteRepositoryImpl
import com.kashapovrush.network_api.repository.SearchRepository
import com.kashapovrush.network_api.repository.WeatherRepository
import com.kashapovrush.network_impl.api.ApiFactory
import com.kashapovrush.network_impl.api.ApiService
import com.kashapovrush.network_impl.repository.SearchRepositoryImpl
import com.kashapovrush.network_impl.repository.WeatherRepositoryImpl
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
        fun provideAppDatabase(context: Context): AppDatabase =  AppDatabase.getInstance(context)

        @[Provides ApplicationScope]
        fun provideCityDao(database: AppDatabase): CityDao = database.cityDao()
    }
}