package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util

import android.content.Context
import android.graphics.Bitmap
import androidx.core.net.toUri
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.BoundingBoxProcessor
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.INPUT_IMAGE_TYPE
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.INPUT_MEAN
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.INPUT_STANDARD_DEVIATION
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.MODEL_PATH
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.OUTPUT_IMAGE_TYPE
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper.Companion.TEMP_CLASSES
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorListener
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ModelLabelExtractor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectorHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageConverter: ImageConverter,
    private val modelLabelExtractor: ModelLabelExtractor
) : ImageDetectorHelper {

    private var imageDetectorListener: ImageDetectorListener? = null

    private var interpreter: Interpreter? = null
    private var labels = mutableListOf<String>()

    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numChannel = 0
    private var numElements = 0

    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(INPUT_MEAN, INPUT_STANDARD_DEVIATION))
        .add(CastOp(INPUT_IMAGE_TYPE))
        .build()

    override fun setImageDetectorListener(listener: ImageDetectorListener) {
        imageDetectorListener = listener
    }

    override fun removeImageDetectorListener() {
        imageDetectorListener = null
    }

    override suspend fun setupImageDetector() = withContext(Dispatchers.IO) {
        clearResource()

        val model = FileUtil.loadMappedFile(context, MODEL_PATH)
        ensureActive()
        val options = Interpreter.Options()
        options.numThreads = 4
        interpreter = Interpreter(model, options)

        val inputShape = interpreter?.getInputTensor(0)?.shape() ?: return@withContext
        val outputShape = interpreter?.getOutputTensor(0)?.shape() ?: return@withContext

        tensorWidth = inputShape[1]
        tensorHeight = inputShape[2]
        numChannel = outputShape[1]
        numElements = outputShape[2]

        if (labels.isEmpty()) {
            ensureActive()
            labels.addAll(modelLabelExtractor.extractNamesFromMetadata(MODEL_PATH))

            if (labels.isNotEmpty()) return@withContext

            ensureActive()
            labels.addAll(modelLabelExtractor.extractNamesFromLabelFile(ImageDetectorHelper.LABEL_PATH))

            if (labels.isNotEmpty()) return@withContext

            labels.addAll(TEMP_CLASSES)
        }
    }

    override fun clearResource() {
        interpreter?.close()
        interpreter = null
    }

    override suspend fun detect(imagePath: String) = withContext(Dispatchers.Default) {
        val isNeedSetupDetectorFirst =
            interpreter == null || tensorWidth == 0 || tensorHeight == 0 || numChannel == 0 || numElements == 0
        if (isNeedSetupDetectorFirst) {
            setupImageDetector()
        }
        val imageFile = File(imagePath)
        val imageUri = imageFile.toUri()
        val imageBitmap = imageConverter.convertImageUriToBitmap(imageUri)
        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, tensorWidth, tensorHeight, false)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)
        val processedImage = imageProcessor.process(tensorImage)
        val imageBuffer = processedImage.buffer

        val output = TensorBuffer.createFixedSize(
            intArrayOf(
                1,
                numChannel,
                numElements
            ),
            OUTPUT_IMAGE_TYPE
        )

        try {
            interpreter?.run(imageBuffer, output.buffer)
            val bestBoxes = BoundingBoxProcessor.getBoundingBox(
                modelOutputFloatArray = output.floatArray,
                numChannels = numChannel,
                numElements = numElements,
                labels = labels
            )
            if (bestBoxes != null) {
                imageDetectorListener?.onDetect(bestBoxes, imageBitmap.width, imageBitmap.height)
            } else imageDetectorListener?.onEmptyDetect()
        } catch (e: Exception) {
            imageDetectorListener?.onError(e)
        }
        return@withContext
    }


}