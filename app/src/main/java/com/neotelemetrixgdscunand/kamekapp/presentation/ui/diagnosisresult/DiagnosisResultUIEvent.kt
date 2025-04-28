package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.UIText

sealed class DiagnosisResultUIEvent {
    data class OnToastMessage(val message: UIText) : DiagnosisResultUIEvent()
    data object OnInputImageInvalid : DiagnosisResultUIEvent()
}