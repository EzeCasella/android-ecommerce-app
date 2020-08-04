package com.example.ecommerce.data.api.products

import com.example.ecommerce.data.api.models.CheckoutRequest
import com.example.ecommerce.data.api.models.Purchase
import com.example.ecommerce.domain.Product
import okhttp3.ResponseBody
import retrofit2.http.*

interface ProductsService {

    @GET("products")
    suspend fun products(): List<Product>

    @Headers("Content-Type:application/json")
    @POST("checkout")
    suspend fun checkoutCart(
        @Header("Authorization") token: String,
        @Body checkoutRequest: CheckoutRequest)
            : ResponseBody

    @GET("purchases")
    suspend fun purchases(
        @Header("Authorization") token: String)
            : List<Purchase>
}