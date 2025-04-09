package com.neotelemetrixgdscunand.kamekapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Login
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.OnBoarding
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Register
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Splash
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.SplashScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.TopLevelPage
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.LoginScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.RegisterScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.onboarding.OnBoardingScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.TopLevelPage


@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isCameraPermissionGranted: Boolean?
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Splash,
    ) {
        composable<Splash> {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Splash> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Login> {
            LoginScreen(
                navigateToTopLevelPage = {
                    navController.navigate(OnBoarding) {
                        popUpTo<Login> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Register> {
            RegisterScreen()
        }

        composable<OnBoarding> {
            val context = LocalContext.current
            OnBoardingScreen(
                navigateUp = {
                    if (context is MainActivity) {
                        context.finish()
                    }
                },
                navigateToTopLevelPage = {
                    navController.navigate(TopLevelPage) {
                        popUpTo<OnBoarding> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<TopLevelPage> {
            TopLevelPage(isCameraPermissionGranted = isCameraPermissionGranted)
        }
    }

}