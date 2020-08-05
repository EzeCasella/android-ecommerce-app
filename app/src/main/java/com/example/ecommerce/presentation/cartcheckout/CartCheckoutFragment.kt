package com.example.ecommerce.presentation.cartcheckout

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.common.application.EMarketApplication
import kotlinx.android.synthetic.main.cart_checkout_fragment.*

class CartCheckoutFragment() : Fragment() {

    private lateinit var cartCheckoutViewModel: CartCheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cartCheckoutViewModel = ViewModelProvider(this).get(CartCheckoutViewModel::class.java)

        return inflater.inflate(R.layout.cart_checkout_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cart = CartCheckoutFragmentArgs.fromBundle(
            requireArguments()
        ).cart

        Log.i("i/CartCheckoutFragment", "Cart prods: ${cart.productsCount.value}")

        cart_lines_list?.apply {
            layoutManager = GridLayoutManager(context, 2)

            addItemDecoration(
                ItemDecorationCheckoutColumns(
                    20
                )
            )

            val adapter =
                CheckoutLineAdapter()
            this.adapter = adapter
            adapter.submitCartlines(cart.cartLines)
        }

        cart_total_price.text = getString(R.string.display_price_2precision, cart.totalCost)

        checkout_button.let {
            if (cart.productsCount.value == 0) {
                // Button greyed as disabled
                it.setBackgroundResource(R.drawable.radius_solid_grey)
                it.setOnClickListener {
                    Toast.makeText(
                        context,
                        getString(R.string.zero_products_alert),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                it.setBackgroundResource(R.drawable.radius_solid_primary)
                it.setOnClickListener {
                    checkout_button.visibility = View.INVISIBLE
                    checkout_progressBar.visibility = View.VISIBLE
                    cartCheckoutViewModel.onCheckoutClick(cart)
                }
            }
        }

        cartCheckoutViewModel.cartCheckedOut.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                checkout_progressBar.visibility = View.INVISIBLE
                cartCheckoutViewModel.onCheckoutComplete()
                Toast.makeText(
                    EMarketApplication.getAppContext(),
                    it,
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigateUp()
            }
        })
    }
}