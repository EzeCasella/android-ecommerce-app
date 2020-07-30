package com.example.ecommerce.data.api.products

import com.example.ecommerce.domain.Product
import retrofit2.http.GET

interface ProductsService {

    @GET("products")
    suspend fun products(): List<Product>

}