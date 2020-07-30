package com.example.ecommerce.data.repositories

import android.util.Log
import com.example.ecommerce.data.api.products.ProductsAPIClient
import com.example.ecommerce.domain.Product

interface ProductsRepository {

    suspend fun getAll(): List<Product>

}

class ProductsRepositoryImpl : ProductsRepository {

    private val client = ProductsAPIClient

    override suspend fun getAll(): List<Product> {
        Log.i("i/ProductsRepository","Adentro del getAll")
        return client.products()
    }

}