package com.neotelemetrixgdscunand.kamekapp.domain.usecase

import com.neotelemetrixgdscunand.kamekapp.domain.model.AnalysisSession

interface GetCocoaAnalysisSessionUseCase {
    suspend operator fun invoke(id: Int): AnalysisSession
}