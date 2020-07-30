package com.example.ecommerce.data.api.products

import com.example.ecommerce.data.api.RetrofitClient
import com.example.ecommerce.domain.Product


object ProductsAPIClient {

    private const val URL_DEBUG = "https://us-central1-ucu-ios-api.cloudfunctions.net/"

    private var service = RetrofitClient
        .provideRetrofit(URL_DEBUG)
        .create(ProductsService::class.java)

    suspend fun products(): List<Product> = service.products()

}