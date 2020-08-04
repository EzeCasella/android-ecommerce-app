package com.example.ecommerce.domain

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

enum class Category {
    FRUIT, VEGETABLE
}

@Parcelize
class Cart(): Parcelable {

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

    fun remove(product: Product) {
        Log.i("i/Models","REMOVE called prods amount: ${_productsCount.value}")

        val cartLine = cartLines.find { it.product.id == product.id }
        cartLine?.removeProduct()
        if (cartLine?.prodAmount == 0) {
            cartLines.remove(cartLine)
        }
        _productsCount.value = _productsCount.value?.minus(1)

    }

    fun has(product: Product): Boolean {
        val cartLine = cartLines.find { it.product.id == product.id }
        return cartLine != null
    }
}

data class CartLine(
    val id: Int,
    val product: Product
) {

    private var _prodAmount = 0
    val prodAmount
        get() = _prodAmount
    var total: BigDecimal = 0.toBigDecimal()
    init {
        _prodAmount = 0
    }

    fun addProduct() {
        _prodAmount++
        total += product.price
    }

    fun removeProduct() {
        _prodAmount--
        total -= product.price
    }
}

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: BigDecimal,
    @Json(name = "photoUrl") val unitImgURL: String?
)

data class Banner(
    @Json(name = "name") val title: String,
    val description: String,
    @Json(name = "photoUrl")val imgURL: String
//                    , TODO("Product related")
)