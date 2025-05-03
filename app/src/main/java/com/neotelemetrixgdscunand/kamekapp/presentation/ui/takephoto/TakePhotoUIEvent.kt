package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import com.neotelemetrixgdscunand.kamekapp.presentation.utils.UIText

interface TakePhotoUIEvent {
    data class OnToastMessage(val message: UIText) : TakePhotoUIEvent
    data class NavigateToResult(
        val sessionName: String,
        val imagePath: String
    ) : TakePhotoUIEvent
}