package com.neotelemetrixgdscunand.kamekapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var windowInsetController:WindowInsetsControllerCompat

    private var isCameraPermissionGranted by mutableStateOf<Boolean?>(null)

    private val cameraPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        isCameraPermissionGranted = isGranted
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        windowInsetController = WindowInsetsControllerCompat(window, window.decorView)

        enableEdgeToEdge()
        setContent {
            KamekAppTheme {
                App(
                    isCameraPermissionGranted =  isCameraPermissionGranted
                )
            }
        }
    }

    fun hideStatusBar(){
        windowInsetController.hide(WindowInsetsCompat.Type.statusBars())
    }

    fun showStatusBar(){
        windowInsetController.show(WindowInsetsCompat.Type.statusBars())
    }


    fun checkCameraPermission(){
        val isAlreadyGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if(!isAlreadyGranted){
            cameraPermissionRequest.launch(
                Manifest.permission.CAMERA
            )
        }else isCameraPermissionGranted = true
    }
}
