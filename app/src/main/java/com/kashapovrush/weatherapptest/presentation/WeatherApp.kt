package com.kashapovrush.weatherapptest.presentation

import android.app.Application
import com.kashapovrush.weatherapptest.di.ApplicationComponent
import com.kashapovrush.weatherapptest.di.DaggerApplicationComponent

class WeatherApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)
    }
}