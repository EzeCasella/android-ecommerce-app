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
        getPromos()
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

    private fun getPromos() {
        viewModelScope.fetch(
            {
                productsRepo.getPromos()
            },{
                _banners.value = it
            },{
            //TODO: Proper exception handling
                Log.i("i/MarketListViewModel","Exception: $it")
            }
        )
    }

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
//            TODO: Proper exception handling
            Log.i("i/MarketListViewModel","La exception es: $it")
        })
    }

    fun fetchPurchases(token: String) {
        viewModelScope.fetch({
            productsRepo.purchases(token)
        },{
            Log.i("i/MarketListViewModel","Cantidad de purchases: ${it[0].date}")
        },{
            Log.i("i/MarketListViewModel","Hubo un error: $it")

        })
    }
}