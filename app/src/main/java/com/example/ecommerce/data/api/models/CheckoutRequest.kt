package com.example.ecommerce.data.api.models

import com.example.ecommerce.domain.Cart

data class Line (
    val product_id: Int,
    val quantity: Int
)

data class CheckoutRequest(
    val cart: List<Line>
)

fun Cart.toCheckoutRequest(): CheckoutRequest{
    val linesList = mutableListOf<Line>()
    for (line in this.cartLines) {
        linesList.add(
            Line(
                line.product.id,
                line.prodAmount
            )
        )
    }
    return CheckoutRequest(linesList)
}