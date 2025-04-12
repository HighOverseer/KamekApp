package com.neotelemetrixgdscunand.kamekapp.domain.presentation

import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox


sealed interface ImageDetectorResult {
    data object NoObjectDetected : ImageDetectorResult
    data class Success(
        val boundingBoxes: List<BoundingBox>
    ) : ImageDetectorResult

    data class Error(
        val exception: Exception
    ) : ImageDetectorResult
}