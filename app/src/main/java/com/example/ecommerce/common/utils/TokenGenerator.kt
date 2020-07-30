package com.example.ecommerce.common.utils

import android.os.Handler
import java.util.*

class TokenGenerator {
    companion object {
        fun generateAuthToken(response: (String) -> Unit) {
             Handler().postDelayed({
                 val token = UUID.randomUUID().toString()
                 response.invoke(token)
             }, 1000)
        }
    }
}