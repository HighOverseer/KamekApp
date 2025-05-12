package com.neotelemetrixgdscunand.kamekapp.domain.data

import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCocoa
import com.neotelemetrixgdscunand.kamekapp.domain.model.AnalysisSession
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import kotlinx.coroutines.flow.Flow

interface CocoaAnalysisRepository {
    suspend fun saveDiagnosis(
        sessionName: String,
        imageOrUrlPath: String,
        predictedPrice: Float,
        detectedCocoas: List<DetectedCocoa>
    ): Int

    fun getAllSavedDiagnosisSessions(): Flow<List<AnalysisSession>>

    suspend fun getDiagnosisSession(id: Int): AnalysisSession

    fun getAllSavedDiagnosisSessionPreviews(): Flow<List<DiagnosisSessionPreview>>
}