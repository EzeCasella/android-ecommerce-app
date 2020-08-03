package com.example.ecommerce.data.repositories

import android.util.Log
import com.example.ecommerce.data.api.products.ProductsAPIClient
import com.example.ecommerce.domain.Cart
import com.example.ecommerce.domain.Product
import okhttp3.ResponseBody

interface ProductsRepository {

    suspend fun getAll(): List<Product>

}

class ProductsRepositoryImpl : ProductsRepository {

    private val client = ProductsAPIClient

    override suspend fun getAll(): List<Product> {
        Log.i("i/ProductsRepository","Adentro del getAll")
        return client.products()
    }

    suspend fun checkoutCart(cart: Cart, token: String): ResponseBody {
        return client.checkoutCart(cart, token)
    }

}