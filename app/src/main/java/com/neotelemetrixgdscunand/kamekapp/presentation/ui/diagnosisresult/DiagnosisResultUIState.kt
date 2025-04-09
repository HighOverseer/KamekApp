package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSession

data class DiagnosisResultUIState(
    val isLoading: Boolean = false,
    val diagnosisSession: DiagnosisSession = DiagnosisSession(),
    val imagePreviewPath: String? = null,
    val isDiagnosisTabSelected: Boolean = true
)