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

    private val _products = MutableLiveData<List<Product>>()
    private val products: LiveData<List<Product>>
        get() = _products

    private val cart = Cart()

    val cartLines = cart.cartLines

    val cartProducts: LiveData<Int>
        get() = cart.productsCount

    fun onAddButtonClicked(cartLine: CartLine){
//        TODO(Implement)
        Log.i("i/MarketListViewModel", "Add button clicked for id: ${cartLine.product.id}")
        cart.add(cartLine.product)
        Log.i("i/MarketListViewModel","Cart total: ${cart.totalCost}")
    }
    fun onRemoveButtonClicked(cartLine: CartLine) {
        Log.i("i/MarketListViewModel","#### REMOVE PROD Cart total: ${cart.totalCost}")
        cart.remove(cartLine.product)
    }

    init {
        _banners.value = listOf(
            Banner(1, "Brazilian Bananas", "Product of the Day", "https://pbs.twimg.com/media/DrjlDZnX0AAK8xJ.jpg"),
            Banner(2, "Kiwi", "Product of the Week", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
            Banner(3, "Fresh Avocado", "Product of the Month", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/fresh-avocado-pattern-on-a-green-background-royalty-free-image-1006125552-1561647338.jpg?crop=0.60251xw:1xh;center,top&resize=980:*")
        )

        _products.value = listOf(
            Product(1, "Kiwi", Category.FRUIT,35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg",""),
            Product(2, "Grapefruit", Category.VEGETABLE, 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg",""),
            Product(3, "Watermelon", Category.FRUIT, 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg",""),
            Product(4, "Kiwi", Category.VEGETABLE,35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg",""),
            Product(5, "Grapefruit", Category.VEGETABLE, 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg","")
//            Product(6, "Watermelon", Category.FRUIT, 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg","")
        )

        products.value?.map {
            cart.addEmptyCartLine(it)
        }
    }


}