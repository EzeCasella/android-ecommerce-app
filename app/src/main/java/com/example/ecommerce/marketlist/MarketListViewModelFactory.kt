package com.example.ecommerce.marketlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarketListViewModelFactory (): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketListViewModel::class.java)) {
            return MarketListViewModel() as T
        }
        throw IllegalArgumentException("Unkown ViewModel class.")
    }
}