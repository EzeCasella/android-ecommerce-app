package com.example.ecommerce.cartcheckout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import kotlinx.android.synthetic.main.cart_checkout_fragment.*

class CartCheckoutFragment(): Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.cart_checkout_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cart = CartCheckoutFragmentArgs.fromBundle(requireArguments()).cart

        Log.i("i/CartCheckoutFragment","Cart prods: ${cart.productsCount.value}")

        cart_lines_list?.layoutManager = GridLayoutManager(context, 2)

        val adapter = CheckoutLineAdapter()
        cart_lines_list?.adapter = adapter

        adapter.submitCartlines(cart.cartLines)

        cart_total_price.text = getString(R.string.display_price_2precision, cart.totalCost)
    }
}