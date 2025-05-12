package com.neotelemetrixgdscunand.kamekapp.domain

import com.neotelemetrixgdscunand.kamekapp.domain.model.AnalysisSession
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview

object DomainMapper {
    fun mapDiagnosisSessionToPreview(
        analysisSession: AnalysisSession
    ): DiagnosisSessionPreview {
        return analysisSession.run {
            DiagnosisSessionPreview(
                id, title, imageUrlOrPath, date, predictedPrice
            )
        }
    }
}