package com.kashapovrush.weatherapptest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.kashapovrush.weatherapptest.rootComponent.DefaultRootComponent
import com.kashapovrush.weatherapptest.rootComponent.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            RootContent(
                component = rootComponentFactory.create(
                    defaultComponentContext()
                )
            )
        }
    }
}
