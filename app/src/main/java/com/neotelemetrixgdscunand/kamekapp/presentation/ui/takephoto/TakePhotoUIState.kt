package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

data class TakePhotoUIState(
    val isConfirmationDialogShown:Boolean = false,
    val isUsingBackCamera:Boolean = true,
    val isCameraOpen:Boolean = true,
    val canUserInteractWithDialog:Boolean = true
)
