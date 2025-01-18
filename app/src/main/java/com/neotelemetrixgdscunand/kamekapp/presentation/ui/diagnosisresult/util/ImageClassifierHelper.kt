package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import com.neotelemetrixgdscunand.kamekapp.ml.ModelDisease
import com.neotelemetrixgdscunand.kamekapp.ml.ModelPriceUpdated
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ImageClassifierHelper(
    private val context: Context,
) {

    suspend fun classify(imageUri: Uri):DiagnosisOutput =
        withContext(Dispatchers.Default){
            val cacaoDiseaseDeferred = async { classifyForDisease(imageUri) }
            val damageLevelDeferred = async { classifyForDamageLevel(imageUri) }

            var damageLevel = damageLevelDeferred.await()
            var predictedPrice = 2000 * (1 - damageLevel).roundOffDecimal()

            val disease =  cacaoDiseaseDeferred.await() ?: CacaoDisease.NONE
            if(disease == CacaoDisease.NONE){
                predictedPrice = 2000f
                damageLevel = 0.0f
            }

            return@withContext DiagnosisOutput(
                disease = disease,
                damageLevel = damageLevel,
                sellPrice = predictedPrice
            )
        }




    private suspend fun classifyForDisease(imageUri: Uri):CacaoDisease? = withContext(Dispatchers.Default){

        val model = ModelDisease.newInstance(context)

        val image = imageUriToBitmap2(imageUri, 255)

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(255, 255, ResizeOp.ResizeMethod.BILINEAR))
            //.add(NormalizeOp(0f, 255f))
            .build()


        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(image)
        val processedImage = imageProcessor.process(tensorImage)

        val repeatedImageBuffer = ByteBuffer.allocateDirect(32 * 255 * 255 * 3 * 4)
        repeatedImageBuffer.order(ByteOrder.nativeOrder())
        repeatedImageBuffer.clear()

        repeat(32){
            repeatedImageBuffer.put(processedImage.buffer)
        }



        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(32, 255, 255, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(repeatedImageBuffer, intArrayOf(32, 255, 255, 3))


        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer


        val classes = listOf(
            CacaoDisease.HELOPELTHIS,
            CacaoDisease.NONE,
            CacaoDisease.BLACKPOD
        )

        val sortedResult = outputFeature0
            .floatArray
            .slice(0..2)
            .sortedDescending()
        val classNames = sortedResult.let {
            val outputNames = mutableListOf<CacaoDisease>()

            for (result in sortedResult){
                val index = outputFeature0.floatArray.indexOfFirst { it == result }

                if(index == -1) continue

                outputNames.add(classes[index])
            }

            outputNames
        }

        model.close()

        return@withContext if(sortedResult.first() < 0.5f){
            null
        }else classNames.first()
    }

    private suspend fun classifyForDamageLevel(imageUri: Uri):Float = withContext(Dispatchers.Default){
        val model = ModelPriceUpdated.newInstance(context)

        val image = imageUriToBitmap2(imageUri, 100)
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(100, 100, ResizeOp.ResizeMethod.BILINEAR))
            //.add(NormalizeOp(0f, 255f))
            .build()


        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(image)
        val processedImage = imageProcessor.process(tensorImage)

        val repeatedImageBuffer = ByteBuffer.allocateDirect(100 * 100 * 3 * 4)
        repeatedImageBuffer.order(ByteOrder.nativeOrder())
        repeatedImageBuffer.put(processedImage.buffer)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(repeatedImageBuffer, intArrayOf(1, 100, 100, 3))


        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val sortedResult = outputFeature0
            .floatArray
            .sortedDescending()
            .slice(0..2)

        outputFeature0.floatArray.forEach {
            println(it)
        }

        val classes = listOf(
            0.15f, //helopelthis
            0.3f, //helopelthis
            0f, // healthy
            0.3f, //Blackpod
            0.6f, //Blackpod
            0.9f //Blackpod
        )

        val classNames = sortedResult.let {
            val outputNames = mutableListOf<Float>()

            for (result in sortedResult){
                val index = outputFeature0.floatArray.indexOfFirst { it == result }

                if(index == -1) continue

                outputNames.add(classes[index])
            }

            outputNames
        }

        model.close()

        classNames.first()
    }


    private fun imageUriToBitmap2(imageUri: Uri, imageSize: Int = 128): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            var bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                decoder.setTargetSampleSize(1) // shrinking by
                decoder.isMutableRequired =
                    true
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
            bitmap
        } else {
            null
        }
    }
}