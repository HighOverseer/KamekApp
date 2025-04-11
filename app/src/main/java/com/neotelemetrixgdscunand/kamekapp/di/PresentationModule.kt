package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kamekapp.domain.presentation.CaptureImageFileHandler
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ModelLabelExtractor
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.ImageDetectorHelperImpl
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.ModelLabelExtractorImpl
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util.CapturedImageFileHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationModule {

    @Binds
    @Singleton
    abstract fun bindImageDetectorHelper(imageDetectorHelperImpl: ImageDetectorHelperImpl): ImageDetectorHelper

    @Binds
    @Singleton
    abstract fun bindModelLabelExtractor(modelLabelExtractorImpl: ModelLabelExtractorImpl): ModelLabelExtractor

    @Binds
    @Singleton
    abstract fun bindCapturedImageFileHandler(capturedImageFileHandlerImpl: CapturedImageFileHandlerImpl): CaptureImageFileHandler

}