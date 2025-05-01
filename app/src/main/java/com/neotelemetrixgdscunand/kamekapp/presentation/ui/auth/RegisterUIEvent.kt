package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth

import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.UIText

sealed interface RegisterUIEvent {
    data class OnRegisterFailed(val messageUIText: UIText) : RegisterUIEvent
    data class OnRegisterSuccess(val userName: String) : RegisterUIEvent
}