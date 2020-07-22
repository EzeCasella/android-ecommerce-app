package com.example.ecommerce.marketlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.domain.Banner

class MarketListViewModel : ViewModel() {

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>>
        get() = _banners

    init {
        _banners.value = listOf(
            Banner(1, "Brazilian Bananas", "Product of the month", ""),
            Banner(1, "Brazilian Bananas", "Product of the month", ""),
            Banner(1, "Brazilian Bananas", "Product of the month", "")
        )
    }
}