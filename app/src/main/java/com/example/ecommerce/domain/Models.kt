package com.example.ecommerce.domain

import java.math.BigDecimal

enum class Category {
    FRUIT, VEGETABLE
}

data class Cart(val total: BigDecimal,
                val checkedOut: Boolean
//                , TODO("CartLine collection")
){
//    fun isEmpty(){
//        TODO("Calculated from the items within.")
//    }
}

data class CartLine(val id: Int,
                    val prodAmount: Int,
                    val total: BigDecimal
//                    , TODO("Product related")
)

data class Product(val id: Int,
                   val name: String,
                   val category: Category,
                   val price: BigDecimal,
                   val unitImgURL: String,
                   val bundleImgURL: String
)

data class Banner(val id: Int,
                      val title: String,
                      val description: String,
                      val imgURL: String
//                    , TODO("Product related")
)