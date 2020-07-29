package com.example.ecommerce.marketlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ecommerce.domain.*

class MarketListViewModel : ViewModel() {

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>>
        get() = _banners

    private var _products = mutableListOf<Product>()

    val cart = Cart()

    private lateinit var _cartLines: MutableList<CartLine>
    val cartLines: List<CartLine>
        get() = _cartLines

    val cartProducts= cart.productsCount

    init {
        _banners.value = listOf(
            Banner(1, "Brazilian Bananas", "Product of the Day", "https://pbs.twimg.com/media/DrjlDZnX0AAK8xJ.jpg"),
            Banner(2, "Kiwi", "Product of the Week", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
            Banner(3, "Fresh Avocado", "Product of the Month", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/fresh-avocado-pattern-on-a-green-background-royalty-free-image-1006125552-1561647338.jpg?crop=0.60251xw:1xh;center,top&resize=980:*")
        )

        _products = mutableListOf(
            Product(1, "Kiwi", Category.FRUIT,35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg",""),
            Product(2, "Grapefruit", Category.VEGETABLE, 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg",""),
            Product(3, "Watermelon", Category.FRUIT, 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg",""),
            Product(4, "Kiwi", Category.VEGETABLE,35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg",""),
            Product(5, "Grapefruit", Category.VEGETABLE, 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg","")
//            Product(6, "Watermelon", Category.FRUIT, 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg","")
        )

        setupCartLines()
    }

    private fun setupCartLines(){
        _cartLines = mutableListOf<CartLine>()

        this._products.forEach { product ->
            _cartLines.add(CartLine(_cartLines.size, product ))
        }
    }

    fun onAddButtonClicked(cartLine: CartLine){
        _cartLines.find { it.id == cartLine.id }?.addProduct()
        cart.add(cartLine.product)
    }
    fun onRemoveButtonClicked(cartLine: CartLine) {
        Log.i("i/MarketListViewModel","#### REMOVE PROD Cart total: ${cart.totalCost}")
        _cartLines.find { it.id == cartLine.id }?.removeProduct()
        cart.remove(cartLine.product)
    }
}