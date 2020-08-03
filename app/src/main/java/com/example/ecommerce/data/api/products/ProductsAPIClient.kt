package com.example.ecommerce.data.api.products

import com.example.ecommerce.data.api.RetrofitClient
import com.example.ecommerce.data.api.models.toCheckoutRequest
import com.example.ecommerce.domain.Cart
import com.example.ecommerce.domain.Product


object ProductsAPIClient {

    private const val URL_DEBUG = "https://us-central1-ucu-ios-api.cloudfunctions.net/"

    private var service = RetrofitClient
        .provideRetrofit(URL_DEBUG)
        .create(ProductsService::class.java)

    suspend fun products(): List<Product> = service.products()

    suspend fun checkoutCart(cart: Cart, token: String) = service.checkoutCart( token, cart.toCheckoutRequest())

}