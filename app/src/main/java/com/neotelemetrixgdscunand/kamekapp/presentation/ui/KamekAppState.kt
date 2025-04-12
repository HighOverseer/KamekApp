package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.view.WindowInsetsControllerCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun rememberKamekAppState(
    windowInsetsController: WindowInsetsControllerCompat
): KamekAppState{
    return remember(windowInsetsController) {
        KamekAppState(
            windowInsetsController
        )
    }
}

@Stable
class KamekAppState(
    private val windowInsetsController: WindowInsetsControllerCompat
) {
    private var isCameraPermissionGranted by mutableStateOf<Boolean?>(null)
    val provideIsCameraPermissionGranted = { isCameraPermissionGranted }

    fun hideStatusBar() {
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    }

    fun showStatusBar() {
        windowInsetsController.show(WindowInsetsCompat.Type.statusBars())
    }

    @Composable
    fun rememberCameraPermissionRequest():ManagedActivityResultLauncher<String, Boolean>
    = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isCameraPermissionGranted = isGranted
    }

    fun checkCameraPermission(
        context: Context,
        cameraPermissionRequest:ManagedActivityResultLauncher<String, Boolean>
    ){
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

}