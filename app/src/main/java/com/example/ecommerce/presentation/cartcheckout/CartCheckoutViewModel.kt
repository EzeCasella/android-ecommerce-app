package com.example.ecommerce.presentation.cartcheckout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.R
import com.example.ecommerce.common.application.EMarketApplication
import com.example.ecommerce.common.utils.PreferenceHelper
import com.example.ecommerce.common.utils.fetch
import com.example.ecommerce.common.utils.get
import com.example.ecommerce.data.repositories.ProductsRepositoryImpl
import com.example.ecommerce.data.domain.Cart

class CartCheckoutViewModel() : ViewModel() {
    private val productsRepo = ProductsRepositoryImpl()

    private var _cartCheckedOut = MutableLiveData<String>()
    val cartCheckedOut: LiveData<String>
        get() = _cartCheckedOut

    fun onCheckoutClick(cart: Cart) {
        val token: String? =
            PreferenceHelper.getEMarketPreferences().get(PreferenceHelper.Key.ACCESS_TOKEN)

        if (token != null) {
            viewModelScope.fetch({
                productsRepo.checkoutCart(cart, token)
            }, {
                cart.onCheckOut()
                _cartCheckedOut.value = it.string()
            }, {
                _cartCheckedOut.value =
                    EMarketApplication.getAppContext().resources.getString(R.string.failed_checkout_alert)
            })
        } else {
//            TODO: implement null token behaviour
        }

    }

    fun onCheckoutComplete() {
        _cartCheckedOut.value = null
    }
}