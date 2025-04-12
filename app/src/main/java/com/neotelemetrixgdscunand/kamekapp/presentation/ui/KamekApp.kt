package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.LoginScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.RegisterScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.onboarding.OnBoardingScreen

@Composable
fun KamekApp(
    modifier: Modifier = Modifier,
    rootNavHostController: NavHostController = rememberNavController(),
    appState: KamekAppState,
) {
    NavHost(
        modifier = modifier,
        navController = rootNavHostController,
        startDestination = Navigation.Splash,
    ) {
        composable<Navigation.Splash> {
            SplashScreen(
                navigateToAuthPage = {
                    rootNavHostController.navigate(Navigation.Auth) {
                        popUpTo<Navigation.Splash> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Navigation.OnBoarding> {
            OnBoardingScreen(
                navigateUp = rootNavHostController::navigateUp,
                navigateToMainPage = {
                    rootNavHostController.navigate(Navigation.Main) {
                        popUpTo<Navigation.OnBoarding> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        navigation<Navigation.Auth>(
            startDestination = Navigation.Auth.Login
        ){
            composable<Navigation.Auth.Login> {
                LoginScreen(
                    navigateToOnBoarding = {
                        rootNavHostController.navigate(Navigation.OnBoarding) {
                            popUpTo<Navigation.Auth> {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Navigation.Auth.Register> {
                RegisterScreen()
            }
        }

        composable<Navigation.Main>{
            val mainNavController = rememberNavController()
            val state = rememberMainPageState(
                navHostController = mainNavController,
                showStatusBar = appState::showStatusBar,
                hideStatusBar = appState::hideStatusBar
            )

            MainPage(
                state = state,
                mainNavHostController = mainNavController,
                provideIsCameraPermissionGranted = appState.provideIsCameraPermissionGranted,
                checkCameraPermission = appState::checkCameraPermission,
                rememberCameraPermissionRequest = { appState.rememberCameraPermissionRequest() }
            )
        }

    }

}
