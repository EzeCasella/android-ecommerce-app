package com.example.ecommerce.data.api.models

import com.example.ecommerce.data.domain.Product
import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class PurchaseLine (
    val product: Product,
    val quantity: Int
)

data class Purchase (
    @Json(name = "date") private val dateString: String,
    val products: List<PurchaseLine>
    ){

    val date: LocalDateTime
        get() {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return LocalDateTime.parse(dateString, formatter)
        }
}