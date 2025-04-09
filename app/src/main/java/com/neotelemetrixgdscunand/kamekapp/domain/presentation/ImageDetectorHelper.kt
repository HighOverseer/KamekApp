package com.neotelemetrixgdscunand.kamekapp.domain.presentation

import org.tensorflow.lite.DataType

interface ImageDetectorHelper {

    suspend fun setupImageDetector()

    suspend fun detect(imagePath: String)

    fun clearResource()

    fun setImageDetectorListener(listener: ImageDetectorListener)

    fun removeImageDetectorListener()

    companion object {
        val TEMP_CLASSES = List(100) { "class ${it + 1}" }

        const val INPUT_MEAN = 0f
        const val INPUT_STANDARD_DEVIATION = 255f
        val INPUT_IMAGE_TYPE = DataType.FLOAT32
        val OUTPUT_IMAGE_TYPE = DataType.FLOAT32
        const val CONFIDENCE_THRESHOLD = 0.1f
        const val IOU_THRESHOLD = 0.5f

        const val MODEL_PATH = "rev_2.tflite"
        const val LABEL_PATH: String = "labels.txt"
    }
}