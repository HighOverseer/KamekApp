package com.neotelemetrixgdscunand.kamekapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.KamekApp
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.rememberKamekAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

        enableEdgeToEdge()
        setContent {
            KamekAppTheme {
                val rootNavHostController = rememberNavController()
                val appState = rememberKamekAppState(
                    windowInsetsController,
                    rootNavHostController
                )

                KamekApp(
                    appState = appState,
                    rootNavHostController = rootNavHostController
                )
            }
        }
    }
}
