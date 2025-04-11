package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util

import com.dicoding.asclepius.domain.common.StringRes

interface TakePhotoUIEvent {
    data class ToastMessageEvent(val message: StringRes) : TakePhotoUIEvent
    data class NavigateToResult(
        val sessionName: String,
        val imagePath: String
    ) : TakePhotoUIEvent
}