package com.neotelemetrixgdscunand.kamekapp.domain.presentation

import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox

interface ImageDetectorListener {
    fun onEmptyDetect()
    fun onDetect(
        boundingBoxes: List<BoundingBox>,
        imageWidth: Int,
        imageHeight: Int
    )

    fun onError(
        e: Exception
    )
}