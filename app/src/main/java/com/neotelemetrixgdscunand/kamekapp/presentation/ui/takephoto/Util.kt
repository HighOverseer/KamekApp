package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.ImageCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider():ProcessCameraProvider{
    return suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
}

fun captureImage(
    imageCapture:ImageCapture,
    context: Context,
    onSuccess:(Uri) -> Unit,
    onError:(ImageCaptureException) -> Unit = {}
){
    val outputFile = createCustomTempFile(context)
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(outputFile)
        .build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onSuccess(outputFileResults.savedUri ?: outputFile.toUri())
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

fun createCustomTempFile(context: Context, extension:String = "jpeg"): File {
    val fileDir = context.externalCacheDir
    val customTempFile = File.createTempFile("temp", ".$extension", fileDir)
    return customTempFile
}

