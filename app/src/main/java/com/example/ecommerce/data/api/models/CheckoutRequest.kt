package com.example.ecommerce.data.api.models

import com.example.ecommerce.domain.Cart

data class CheckoutLine (
    val product_id: Int,
    val quantity: Int
)

data class CheckoutRequest(
    val cart: List<CheckoutLine>
)

fun Cart.toCheckoutRequest(): CheckoutRequest{
    val linesList = mutableListOf<CheckoutLine>()
    for (line in this.cartLines) {
        linesList.add(
            CheckoutLine(
                line.product.id,
                line.prodAmount
            )
        )
    }
    return CheckoutRequest(linesList)
}