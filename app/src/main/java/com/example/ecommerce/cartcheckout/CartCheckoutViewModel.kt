package com.example.ecommerce.cartcheckout

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.MyApplication
import com.example.ecommerce.R
import com.example.ecommerce.common.utils.fetch
import com.example.ecommerce.data.repositories.ProductsRepositoryImpl
import com.example.ecommerce.domain.Cart

class CartCheckoutViewModel() : ViewModel() {
    private val productsRepo = ProductsRepositoryImpl()

    private var _cartCheckedOut = MutableLiveData<Boolean>(false)
    val cartCheckedOut: LiveData<Boolean>
        get() = _cartCheckedOut

    fun onCheckoutClick(cart: Cart, token: String){
        viewModelScope.fetch ( {
            productsRepo.checkoutCart(cart, token)
        }, {
            Toast.makeText(
                MyApplication.getAppContext(),
                it.string(),
                Toast.LENGTH_LONG)
                .show()
            cart.onCheckOut()
            _cartCheckedOut.value = true
        }, {
            Toast.makeText(
                MyApplication.getAppContext(),
                R.string.failed_checkout_alert,
                Toast.LENGTH_LONG)
                .show()
        })
    }

    fun onCheckoutComplete(){
        _cartCheckedOut.value = false
    }
}