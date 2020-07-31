package com.example.ecommerce.marketlist

import android.util.Log
import androidx.lifecycle.*
import com.example.ecommerce.common.utils.fetch
import com.example.ecommerce.data.repositories.ProductsRepositoryImpl
import com.example.ecommerce.domain.*
import kotlinx.coroutines.launch

class MarketListViewModel : ViewModel() {

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>>
        get() = _banners

    private lateinit var _products : List<Product>

    val cart = Cart()

    private var _cartLines = MutableLiveData<List<CartLine>>()
    val cartLines: LiveData<List<CartLine>>
        get() = _cartLines

    val cartProducts= cart.productsCount
    private val productsRepo = ProductsRepositoryImpl()

    init {

        _banners.value = listOf(
            Banner(1, "Brazilian Bananas", "Product of the Day", "https://pbs.twimg.com/media/DrjlDZnX0AAK8xJ.jpg"),
            Banner(2, "Kiwi", "Product of the Week", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
            Banner(3, "Fresh Avocado", "Product of the Month", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/fresh-avocado-pattern-on-a-green-background-royalty-free-image-1006125552-1561647338.jpg?crop=0.60251xw:1xh;center,top&resize=980:*")
        )

//        _products = mutableListOf(
//            Product(1, "Kiwi", "Fruit",35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
//            Product(2, "Grapefruit", "Vegetable", 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg"),
//            Product(3, "Watermelon", "Fruit", 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg"),
//            Product(4, "Kiwi", "Vegetable",35.toBigDecimal(),"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/close-up-of-fruits-over-purple-background-royalty-free-image-733479343-1536169938.jpg"),
//            Product(5, "Grapefruit", "Vegetable", 45.toBigDecimal(),"https://wiselivingmagazine.co.uk/wp-content/uploads/2020/06/Health-benefits-grapefruit-main.jpg")
////            Product(6, "Watermelon", "Fruit", 45.toBigDecimal(),"https://img.etimg.com/photo/msid-69534798,quality-100/watermelons1.jpg","")
//        )
        getProducts()
    }

    private fun setupCartLines(){
        _cartLines.value = listOf()
        this._products.forEach { product ->
            _cartLines.value = (_cartLines.value?.plus((CartLine(_cartLines.value!!.size, product ))))?.toList()
        }
    }

    fun onAddButtonClicked(cartLine: CartLine){
        _cartLines.value?.find { it.id == cartLine.id }?.addProduct()
        cart.add(cartLine.product)
    }
    fun onRemoveButtonClicked(cartLine: CartLine) {
        Log.i("i/MarketListViewModel","#### REMOVE PROD Cart total: ${cart.totalCost}")
        _cartLines.value?.find { it.id == cartLine.id }?.removeProduct()
        cart.remove(cartLine.product)
    }
//
//    // Create a Coroutine scope using a job to be able to cancel when needed
//    private var viewModelJob = Job()
//
//    // the Coroutine runs using the Main (UI) dispatcher
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun getProducts() {
        Log.i("i/MarketListViewModel","Inside getProducts")
//        viewModelScope.launch {
//            var response = productsRepo.getAll()
//            _products = response
//            setupCartLines()
//        }
        viewModelScope.fetch({
            productsRepo.getAll()
        },{
            _products = it
            setupCartLines()
        },{
            Log.i("i/MarketListViewModel","La exception es: $it")
        })
    }
}