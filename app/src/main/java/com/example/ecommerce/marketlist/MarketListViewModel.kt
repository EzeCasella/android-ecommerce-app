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
            Banner(1, "Brazilian Bananas", "Product of the Day", "https://pbs.twimg.com/media/DrjlDZnX0AAK8xJ.jpg"),
            Banner(2, "Kiwi", "Product of the Week", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
            Banner(3, "Fresh Avocado", "Product of the Month", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/fresh-avocado-pattern-on-a-green-background-royalty-free-image-1006125552-1561647338.jpg?crop=0.60251xw:1xh;center,top&resize=980:*")
        )
    }
}