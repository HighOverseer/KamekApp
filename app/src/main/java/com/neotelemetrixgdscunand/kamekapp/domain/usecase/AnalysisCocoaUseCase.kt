package com.neotelemetrixgdscunand.kamekapp.domain.usecase

import com.neotelemetrixgdscunand.kamekapp.domain.common.CocoaAnalysisError
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.model.AnalysisSession


interface AnalysisCocoaUseCase {
    suspend operator fun invoke(
        sessionName: String,
        imagePath: String
    ): Result<AnalysisSession, CocoaAnalysisError>
}