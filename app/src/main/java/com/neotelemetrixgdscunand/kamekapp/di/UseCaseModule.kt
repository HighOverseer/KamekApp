package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kakaoxpert.domain.interactor.GetCocoaAnalysisSessionInteractor
import com.neotelemetrixgdscunand.kamekapp.domain.data.CocoaAnalysisRepository
import com.neotelemetrixgdscunand.kamekapp.domain.interactor.AnalysisCocoaInteractor
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.CocoaImageDetectorHelper
import com.neotelemetrixgdscunand.kamekapp.domain.usecase.AnalysisCocoaUseCase
import com.neotelemetrixgdscunand.kamekapp.domain.usecase.GetCocoaAnalysisSessionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideAnalysisCocoaUseCase(
        cocoaAnalysisRepository: CocoaAnalysisRepository,
        cocoaImageDetectorHelper: CocoaImageDetectorHelper
    ): AnalysisCocoaUseCase {
        return AnalysisCocoaInteractor(cocoaImageDetectorHelper, cocoaAnalysisRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCocoaAnalysisSessionUseCase(
        cocoaAnalysisRepository: CocoaAnalysisRepository
    ): GetCocoaAnalysisSessionUseCase {
        return GetCocoaAnalysisSessionInteractor(cocoaAnalysisRepository)
    }
}