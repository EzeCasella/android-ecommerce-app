package com.example.ecommerce.cartcheckout

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.common.utils.fetch
import com.example.ecommerce.data.repositories.ProductsRepositoryImpl
import com.example.ecommerce.domain.Cart
import kotlinx.coroutines.launch

class CartCheckoutViewModel() : ViewModel() {
    private val productsRepo = ProductsRepositoryImpl()

    init {

    }

    // TODO: Change fragment usage to MyApplication context implementation
    fun onCheckoutClick(cart: Cart, token: String, fragment: Fragment){
        viewModelScope.fetch ( {
            productsRepo.checkoutCart(cart, token)
        }, {
            Toast.makeText(fragment.context, it.string(), Toast.LENGTH_LONG).show()
//            cart.empty()
        }, {
            Toast.makeText(fragment.context, R.string.failed_checkout_alert, Toast.LENGTH_LONG).show()
        })
    }
}