package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.DiagnosisSessionComposeStable

data class DiagnosisResultUIState(
    val isLoading: Boolean = false,
    val diagnosisSession: DiagnosisSessionComposeStable = DiagnosisSessionComposeStable(),
    val imagePreviewPath: String? = null
)