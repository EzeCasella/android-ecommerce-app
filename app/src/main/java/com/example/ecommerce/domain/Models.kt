package com.example.ecommerce.domain

import java.math.BigDecimal

enum class Category {
    FRUIT, VEGETABLE
}

class Cart() {

    var checkedOut: Boolean = false
    val cartLines = mutableSetOf<CartLine>()
    val total: BigDecimal
        get() {
            var result = 0.toBigDecimal()
            cartLines.forEach {
                result += it.total
            }
            return result
        }

    private var cartLineId = 0
    fun add(product: Product) {
        val index = cartLines.indexOfFirst {
            it.product.id == product.id
        }
        if (index != -1) {
        }

        val cartLine = cartLines.find { it.product.id == product.id }
        cartLine?.addProduct() ?: cartLines.add(CartLine(cartLineId++, product))
    }

    fun isEmpty(): Boolean {
        return cartLines.isEmpty()
    }
}

data class CartLine(
    val id: Int,
    val product: Product
) {

    var prodAmount = 0
    var total: BigDecimal = 0.toBigDecimal()

    init {
        this.addProduct()
    }

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