package com.example.stuntkids.util

import android.content.Context
import android.util.Log
import com.example.stuntkids.ml.BestModel
import com.example.stuntkids.model.StuntingRequest
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.text.toFloat

class StuntingCheckerAnalyzer(
    private val context: Context
) {
    fun getOutput(request: StuntingRequest): String {
        val model = BestModel.newInstance(context)

        val floatArray =
            normalizeArray(
                floatArrayOf(
                    request.age.toFloat(),
                    request.gender.toFloat(),
                    request.height.toFloat()
                )
            )

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(3 * 4) // 3 features * 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder())
        floatArray.forEach { byteBuffer.putFloat(it) }
        byteBuffer.rewind()
        inputFeature0.loadBuffer(byteBuffer)
        Log.d("StuntModel", "Input: ${inputFeature0.floatArray.toList()}")

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val list = outputFeature0.floatArray.toList()
        Log.d("StuntModel", "Output: ${list}")

        val result = when (maxIndex(list)) {
            0 -> "Normal"
            1 -> "Severly Stunted"
            2 -> "Very Stunted"
            3 -> "Tinggi"
            else -> "Unknown"
        }

        model.close()

        Log.d("StuntModel", "Result: $result")
        return result
    }

    fun maxIndex(array: List<Float>): Int {
        var maxIndex = 0
        var maxValue = array[0]
        for (i in array.indices) {
            if (array[i] > maxValue) {
                maxIndex = i
                maxValue = array[i]
            }
        }
        return maxIndex
    }

    fun normalizeArray(array: FloatArray): FloatArray {
        val min = array.minOrNull() ?: throw IllegalArgumentException("Array must not be empty")
        val max = array.maxOrNull() ?: throw IllegalArgumentException("Array must not be empty")
        val range = max - min

        return array.map { (it - min) / range }.toFloatArray()
    }
}