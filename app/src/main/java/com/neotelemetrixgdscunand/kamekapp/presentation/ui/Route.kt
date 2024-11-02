package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import kotlinx.serialization.Serializable


@Serializable
sealed class Route

@Serializable
data object Splash:Route()

@Serializable
data object Login:Route()

@Serializable
data object Register:Route()

@Serializable
data object TopLevelPage:Route()

@Serializable
sealed class BottomBarRoute:Route(){
    @Serializable
    data object Home:BottomBarRoute()

    @Serializable
    data object Diagnosis:BottomBarRoute()

    @Serializable
    data object Account:BottomBarRoute()
}

@Serializable
data object TakePhotoRoute:Route()