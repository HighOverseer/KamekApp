package com.neotelemetrixgdscunand.kamekapp.presentation.model

import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText


data class WeatherForecastOverviewDui(
    val maxTemperature: UIText,
    val minTemperature: UIText,
    val currentTemperature: UIText,
    val windVelocity: UIText,
    val humidity: UIText,
    val rainfall: UIText,
    val name: UIText,
    val iconResourceId:Int,
)