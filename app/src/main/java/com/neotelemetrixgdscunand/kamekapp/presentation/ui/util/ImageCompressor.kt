package com.neotelemetrixgdscunand.kamekapp.presentation.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class ImageCompressor(
    private val context: Context
) {

    suspend fun compressBitmap(
        bitmap: Bitmap,
        maxImageSizeKB: Int = DEFAULT_MAX_IMAGE_SIZE_KB
    ): Bitmap =
        withContext(Dispatchers.Default) {
            ensureActive()

            var compressQuality = 100
            var streamLength: Int
            val bmpStream = ByteArrayOutputStream()

            do {
                ensureActive()
                bmpStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPictByteArray = bmpStream.toByteArray()
                streamLength = bmpPictByteArray.size
                compressQuality -= 5
            } while (streamLength > maxImageSizeKB && compressQuality > 0)

            val compressedByteArray = bmpStream.toByteArray()

            return@withContext BitmapFactory.decodeByteArray(
                compressedByteArray,
                0,
                compressedByteArray.size
            )
        }


    suspend fun compressImage(
        uri: Uri?,
        maxImageSizeKB: Int = DEFAULT_MAX_IMAGE_SIZE_KB
    ): ByteArray? {

        if (uri == null) return null

        return withContext(Dispatchers.IO) {
            val mimeType = context.contentResolver.getType(uri)
            val inputBytes = context
                .contentResolver
                .openInputStream(uri)
                ?.use { inputStream ->
                    inputStream.readBytes()
                } ?: return@withContext null

            ensureActive()

            withContext(Dispatchers.Default) {
                val bitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size)

                ensureActive()

                val compressFormat = when (mimeType) {
                    "image/jpeg", "image/jpg" -> Bitmap.CompressFormat.JPEG
                    "image/png" -> Bitmap.CompressFormat.PNG
                    "image/webp" -> if (Build.VERSION.SDK_INT > 30) {
                        Bitmap.CompressFormat.WEBP_LOSSLESS
                    } else Bitmap.CompressFormat.WEBP

                    else -> Bitmap.CompressFormat.JPEG
                }

                var outputBytes: ByteArray
                var quality = 90

                do {
                    ByteArrayOutputStream().use { outputStream ->
                        bitmap.compress(compressFormat, quality, outputStream)
                        outputBytes = outputStream.toByteArray()
                        quality -= (quality * 0.1).roundToInt()
                    }
                } while (
                    isActive &&
                    outputBytes.size > maxImageSizeKB &&
                    quality > 5 &&
                    compressFormat != Bitmap.CompressFormat.PNG
                )

                outputBytes
            }
        }
    }

    companion object {
        private const val DEFAULT_MAX_IMAGE_SIZE_KB = 512 * 1000 // 500 KB
    }
}