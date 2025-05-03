package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home

import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText

sealed interface HomeUIEvent {
    data class OnLocationResolvableError(val exception: Exception):HomeUIEvent
    data class OnLocationUnknownError(val errorUIText: UIText):HomeUIEvent
    data class OnFailedFetchWeatherForecast(val errorUIText: UIText):HomeUIEvent
}