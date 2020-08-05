package com.example.ecommerce.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.common.utils.PreferenceHelper
import com.example.ecommerce.common.utils.TokenGenerator
import com.example.ecommerce.common.utils.get
import com.example.ecommerce.common.utils.set

class LoginViewModel : ViewModel() {

    private val _mustSetToken = MutableLiveData<Boolean>(true)
    val mustSetToken: LiveData<Boolean>
        get() = _mustSetToken

    init {
        val token: String? = PreferenceHelper.getEMarketPreferences().get(PreferenceHelper.Key.ACCESS_TOKEN)

        if (token != null) {
            _mustSetToken.value = false
        }
    }

    fun tokenSetup(){
//        TODO: Implement
        TokenGenerator.generateAuthToken {
            PreferenceHelper.getEMarketPreferences().set(PreferenceHelper.Key.ACCESS_TOKEN, "Bearer "+it)
            _mustSetToken.value = false
        }
    }
}