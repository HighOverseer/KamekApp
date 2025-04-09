package com.neotelemetrixgdscunand.kamekapp.domain

import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSession
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview

object Mapper {
    fun mapDiagnosisSessionToPreview(
        diagnosisSession: DiagnosisSession
    ): DiagnosisSessionPreview {
        return diagnosisSession.run {
            DiagnosisSessionPreview(
                id, title, imageUrlOrPath, date, predictedPrice
            )
        }
    }
}