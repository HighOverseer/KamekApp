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
data object OnBoarding:Route()

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

@Serializable
data class DiagnosisResultRoute(
    val imagePath:String,
    val outputId:Int?
):Route()

@Serializable
data object News:Route()

@Serializable
data object NewsDetail:Route()

@Serializable
data object Shop:Route()

@Serializable
data object Weather:Route()

