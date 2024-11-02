package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val lensFacing = CameraSelector.LENS_FACING_BACK

    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }

    val cameraxSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraxSelector,
            preview
        )
        preview.surfaceProvider = previewView.surfaceProvider
    }

    AndroidView(
        modifier = modifier,
        factory = { previewView },
    )
}

