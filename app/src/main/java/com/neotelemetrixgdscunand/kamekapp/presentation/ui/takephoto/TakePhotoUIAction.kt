package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

sealed interface TakePhotoUIAction {
    data class OnPickImageFromGalleryResult(val imageUriPath: String?) : TakePhotoUIAction
    data class OnCaptureImageSuccess(val imageUriPath: String?) : TakePhotoUIAction
    data class OnCaptureImageError(val error: Exception) : TakePhotoUIAction
    data class OnConfirmationDialogSubmitted(val submittedSessionName: String) : TakePhotoUIAction
    data object OnConfirmationDialogDismissed : TakePhotoUIAction
    data object OnToggleIsUsingBackCamera : TakePhotoUIAction
}