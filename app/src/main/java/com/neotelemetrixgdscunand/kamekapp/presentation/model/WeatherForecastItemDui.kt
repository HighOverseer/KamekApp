package com.neotelemetrixgdscunand.kamekapp.presentation.model

import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText

data class WeatherForecastItemDui(
    val date: UIText,
    val maxTemperature: UIText,
    val minTemperature: UIText,
    val windVelocity: UIText,
    val humidity: UIText,
    val iconResourceId: Int,
)