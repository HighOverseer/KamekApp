package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
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
    onSuccess:(File) -> Unit,
    onError:(ImageCaptureException) -> Unit = {},
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
                onSuccess(outputFileResults.savedUri?.path?.let { File(it) } ?: outputFile)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

fun createCustomTempFile(context: Context): File {
    val fileDir = context.externalCacheDir
    val customTempFile = File.createTempFile("temp", ".jpeg", fileDir)
    return customTempFile
}

fun renameFile(file: File?, newName: String):File{
    if(file == null) throw Exception("File is null")

    if(!file.exists()) throw Exception("File not found")

    val newFile = File(file.parent, newName)

    file.renameTo(newFile)
    return newFile
}