package com.example.ecommerce.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal

enum class Category {
    FRUIT, VEGETABLE
}

class Cart() {

    var checkedOut: Boolean = false
    val cartLines = mutableListOf<CartLine>()
    val totalCost: BigDecimal
        get() {
            var result = 0.toBigDecimal()
            cartLines.forEach {
                result += it.total
            }
            return result
        }

    private var _productsCount = MutableLiveData<Int>(0)
    val productsCount : LiveData<Int>
        get() = _productsCount

    private var cartLineId = 0

    fun addEmptyCartLine(product: Product) {
        val cartLine = cartLines.find { it.product.id == product.id }

        if (cartLine == null) {
            val newCartLine = CartLine(cartLineId++, product)
            cartLines.add(newCartLine)
        }
    }
    fun add(product: Product) {
        val cartLine = cartLines.find { it.product.id == product.id }

        if (cartLine != null) {
            cartLine.addProduct()
        } else {
            val newCartLine = CartLine(cartLineId++, product)
            newCartLine.addProduct()
            cartLines.add(newCartLine)
        }

        _productsCount.value = _productsCount.value?.plus(1)
        Log.i("i/Models","Products count: ${_productsCount.value}")
    }

//    fun isEmpty(): Boolean {
//        return cartLines.forEach
//    }
}

data class CartLine(
    val id: Int,
    val product: Product
) {

    var prodAmount = 0
    var total: BigDecimal = 0.toBigDecimal()

    fun addProduct() {
        prodAmount++
        total += product.price
    }

    fun removeProduct() {
        prodAmount--
        total -= product.price
    }
}

data class Product(
    val id: Int,
    val name: String,
    val category: Category,
    val price: BigDecimal,
    val unitImgURL: String,
    val bundleImgURL: String
)

data class Banner(
    val id: Int,
    val title: String,
    val description: String,
    val imgURL: String
//                    , TODO("Product related")
)