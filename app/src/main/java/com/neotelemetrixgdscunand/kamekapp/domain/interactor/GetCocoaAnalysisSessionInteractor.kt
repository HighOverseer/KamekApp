package com.neotelemetrixgdscunand.kakaoxpert.domain.interactor

import com.neotelemetrixgdscunand.kamekapp.domain.data.CocoaAnalysisRepository
import com.neotelemetrixgdscunand.kamekapp.domain.model.AnalysisSession
import com.neotelemetrixgdscunand.kamekapp.domain.usecase.GetCocoaAnalysisSessionUseCase

class GetCocoaAnalysisSessionInteractor(
    private val cocoaAnalysisRepository: CocoaAnalysisRepository
) : GetCocoaAnalysisSessionUseCase {
    override suspend fun invoke(id: Int): AnalysisSession {
        return cocoaAnalysisRepository.getDiagnosisSession(id)
    }
}