package com.kashapovrush.weatherapptest.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dagger.Module
import dagger.Provides

@Module
interface PresentationModule {

    companion object {

        @Provides
        fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()
    }
}