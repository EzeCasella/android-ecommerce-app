package com.example.ecommerce.cartcheckout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.repositories.ProductsRepositoryImpl
import com.example.ecommerce.domain.Cart
import kotlinx.coroutines.launch

class CartCheckoutViewModel() : ViewModel() {
    private val productsRepo = ProductsRepositoryImpl()

    init {

    }

    fun onCheckoutClick(cart: Cart, token: String){
        viewModelScope.launch {
            val response = productsRepo.checkoutCart(cart, token)


            Log.i("i/CartCheckoutViewModel","La respuesta del post es: ${response.string()}")
        }
    }
}