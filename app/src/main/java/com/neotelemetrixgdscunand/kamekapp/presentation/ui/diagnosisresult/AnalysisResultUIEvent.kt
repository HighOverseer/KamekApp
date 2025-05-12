package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

sealed class AnalysisResultUIEvent {
    data object OnFailedToAnalyseImage : AnalysisResultUIEvent()
    data object OnInputImageInvalid : AnalysisResultUIEvent()
}