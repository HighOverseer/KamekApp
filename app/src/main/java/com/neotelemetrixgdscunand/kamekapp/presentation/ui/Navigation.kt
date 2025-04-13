package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Navigation {

    @Serializable
    sealed interface Page : Navigation

    @Serializable
    sealed interface Route: Navigation{
        val stringVal: String?
            get() = this::class.java.canonicalName
    }

    @Serializable
    data object Splash : Route

    @Serializable
    data object OnBoarding : Route

    @Serializable
    data object Auth : Page {

        @Serializable
        sealed interface AuthRoute : Route

        @Serializable
        data object Login : AuthRoute

        @Serializable
        data object Register : AuthRoute
    }

    @Serializable
    data object Main : Page {

        @Serializable
        sealed interface MainRoute : Route

        @Serializable
        sealed interface TopLevel : MainRoute {
            @Serializable
            data object Home : TopLevel

            @Serializable
            data object Diagnosis : TopLevel

            @Serializable
            data object Account : TopLevel
        }

        @Serializable
        data object TakePhoto : MainRoute

        @Serializable
        data class DiagnosisResult(
            val newUnsavedSessionImagePath: String?,
            val newSessionName: String?,
            val sessionId: Int?
        ) : MainRoute

        @Serializable
        data object News : MainRoute

        @Serializable
        data object NewsDetail : MainRoute

        @Serializable
        data object Shop : MainRoute

        @Serializable
        data object Weather : MainRoute

        @Serializable
        data object Notification : MainRoute

        @Serializable
        data object CacaoRequest : MainRoute

        @Serializable
        data class CacaoImageDetail(
            val imagePath: String,
            val diagnosisSessionId: Int,
            val detectedCacaoId: Int?
        ) : MainRoute
    }
}
