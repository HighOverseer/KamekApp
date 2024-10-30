package com.neotelemetrixgdscunand.kamekapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Login
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Register
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Splash
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.SplashScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.TopLevelPage
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.LoginScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.RegisterScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.TopLevelPage


@Composable
fun App(
    modifier: Modifier = Modifier,
    navController:NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Splash,
    ){
        composable<Splash> {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(Login){
                        popUpTo<Splash>{
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Login> {
            LoginScreen(
                navigateToTopLevelPage = {
                    navController.navigate(TopLevelPage){
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

        composable<TopLevelPage> {
            TopLevelPage()
        }
    }

}