package pl.kcieslar.wyjazdyosp.model

import androidx.annotation.Keep

@Keep
data class Quarter(
    val name: String,
    val year: Int,
    val quarter: Int
)