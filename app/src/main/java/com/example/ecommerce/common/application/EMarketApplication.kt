package com.example.ecommerce.common.application

import android.app.Application
import android.content.Context

class EMarketApplication : Application() {

    companion object {
        private lateinit var context: Context
        private lateinit var instance: Application

        fun getAppContext(): Context {
            return context
        }

        fun get(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
    }
}