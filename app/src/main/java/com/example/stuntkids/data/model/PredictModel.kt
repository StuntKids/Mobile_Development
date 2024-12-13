package com.example.stuntkids.data.model

data class PredictModel(
    val name: String,
    val age: Int,
    val gender: Int,
    val height: Int,
    val result: String
) {
    fun toRequestText(): String {
        return "$name-$age-$gender-$height-$result"
    }

    companion object {
        fun fromRequestText(text: String): PredictModel? {
            if (text.split("-").size != 5) {
                return null
            }
            val name = text.split("-")[0].removePrefix("\"")
            val age = text.split("-")[1].toInt()
            val gender = text.split("-")[2].toInt()
            val height = text.split("-")[3].toInt()
            val result = text.split("-")[4].removeSuffix("\"")
            return PredictModel(name, age, gender, height, result)
        }
    }
}