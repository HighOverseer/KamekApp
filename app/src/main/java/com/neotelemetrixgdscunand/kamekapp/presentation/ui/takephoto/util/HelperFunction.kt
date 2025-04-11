package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
}


fun createCustomTempFile(context: Context, extension: String = "jpeg"): File {
    val fileDir = context.externalCacheDir
    val customTempFile = File.createTempFile("temp", ".$extension", fileDir)
    return customTempFile
}

