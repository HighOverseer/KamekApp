package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import com.neotelemetrixgdscunand.kamekapp.presentation.dui.DiagnosisSessionDui

data class DiagnosisResultUIState(
    val isLoading: Boolean = false,
    val diagnosisSession: DiagnosisSessionDui = DiagnosisSessionDui(),
    val imagePreviewPath: String? = null
)