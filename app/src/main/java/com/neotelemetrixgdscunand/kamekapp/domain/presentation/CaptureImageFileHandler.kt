package com.neotelemetrixgdscunand.kamekapp.domain.presentation

interface CaptureImageFileHandler {
    suspend fun saveImage(
        imageUriPath:String?,
        imageBytes:ByteArray?,
        fileName:String
    ):String?

    fun deleteImageFile(
        fileUriPath:String
    )
}