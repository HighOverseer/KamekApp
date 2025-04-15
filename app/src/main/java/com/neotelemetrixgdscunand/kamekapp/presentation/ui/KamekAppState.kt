package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun rememberKamekAppState(
    windowInsetsController: WindowInsetsControllerCompat,
    rootNavHostController: NavHostController
): KamekAppState {
    return remember(
        windowInsetsController,
        rootNavHostController
    ) {
        KamekAppState(
            windowInsetsController,
            rootNavHostController
        )
    }
}

@Stable
class KamekAppState(
    private val windowInsetsController: WindowInsetsControllerCompat,
    private val rootNavHostController: NavHostController
) {
    private var isCameraPermissionGranted by mutableStateOf<Boolean?>(null)
    val provideIsCameraPermissionGranted = { isCameraPermissionGranted }

    private fun hideStatusBar() {
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    }

    private fun showStatusBar() {
        windowInsetsController.show(WindowInsetsCompat.Type.statusBars())
    }

    @Composable
    fun rememberCameraPermissionRequest(): ManagedActivityResultLauncher<String, Boolean> =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            isCameraPermissionGranted = isGranted
        }

    fun checkCameraPermission(
        context: Context,
        cameraPermissionRequest: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val isAlreadyGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (!isAlreadyGranted) {
            cameraPermissionRequest.launch(
                Manifest.permission.CAMERA
            )
        } else isCameraPermissionGranted = true
    }

    private val currentRoute: State<String?>
        @Composable get() {
            val navBackStackEntry by rootNavHostController.currentBackStackEntryAsState()
            return remember {
                derivedStateOf {
                    navBackStackEntry?.destination?.route
                }
            }
        }

    private val shouldShowStatusBar: State<Boolean>
        @Composable get() {
            val currentRoute by currentRoute
            return remember {
                derivedStateOf {
                    currentRoute != Navigation.TakePhoto.stringVal
                }
            }
        }

    @Composable
    fun HandleStatusBarVisibilityEffect() {
        val shouldShowStatusBar by shouldShowStatusBar
        LaunchedEffect(shouldShowStatusBar) {
            if (shouldShowStatusBar) {
                showStatusBar()
            } else {
                hideStatusBar()
            }
        }
    }
}