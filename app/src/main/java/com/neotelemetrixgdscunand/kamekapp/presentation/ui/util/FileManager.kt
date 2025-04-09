package com.neotelemetrixgdscunand.kamekapp.presentation.ui.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.createCustomTempFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileManager(
    private val context: Context
) {

    suspend fun saveImage(
        imageUri: Uri?,
        imageBytes: ByteArray?,
        fileName: String = "image"
    ): String? {
        return withContext(Dispatchers.IO) {
            if (imageBytes == null) return@withContext null

            if (imageUri == null) return@withContext null

            val mimeType = context.contentResolver.getType(imageUri)
            val extension = MimeTypeMap
                .getSingleton()
                .getExtensionFromMimeType(mimeType) ?: "jpeg"

            if (imageUri.scheme == "file") {
                val oldFile = imageUri.toFile()
                if (oldFile.exists()) oldFile.delete()
            }


            val file = createCustomTempFile(context, extension = extension)
            file.writeBytes(imageBytes)
            val fullFileName = "$fileName.$extension"
            val renameFileUri = renameFile(file, fullFileName)

            return@withContext renameFileUri.path
        }

    }

    private fun renameFile(file: File?, newName: String): Uri {
        if (file == null) throw Exception("File is null")

        if (!file.exists()) throw Exception("File not found")

        val newFile = File(file.parent, newName)

        file.renameTo(newFile)
        return newFile.toUri()
    }

    fun deleteFile(fileUri: Uri?) {
        if (fileUri == null) return

        if (fileUri.scheme == "file") {
            val file = fileUri.toFile()
            if (file.exists()) file.delete()
        }
    }

}