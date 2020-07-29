package com.example.ecommerce.marketlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ecommerce.R
import com.example.ecommerce.databinding.MarketListFragmentBinding
import kotlinx.android.synthetic.main.list_item_category.view.*
import kotlinx.android.synthetic.main.list_item_product.*
import kotlinx.android.synthetic.main.market_list_fragment.*

class MarketListFragment : Fragment() {

    companion object {
        fun newInstance() = MarketListFragment()
        var searchBarText: String? = ""
    }

    private lateinit var marketListViewModel: MarketListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: MarketListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.market_list_fragment, container, false
        )

        setHasOptionsMenu(true)

        marketListViewModel = ViewModelProvider(this).get(MarketListViewModel::class.java)

        binding.marketListViewModel = marketListViewModel

        binding.lifecycleOwner = this
        /*
        * Banners list setup
        * */
        val bannerLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.bannerList.layoutManager = bannerLayoutManager
        LinearSnapHelper().attachToRecyclerView(binding.bannerList)

        val bannerAdapter = BannerAdapter()
        binding.bannerList.adapter = bannerAdapter

        marketListViewModel.banners.observe(viewLifecycleOwner, Observer {
            it?.let{
                bannerAdapter.submitBannerList(it)
            }
        })

        /*
        * Products list setup
        * */
        val productsLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.productsList.layoutManager = productsLayoutManager

        val productAdapter =  CartLineAdapter( CartLineListener { cartLine ->
            marketListViewModel.onAddButtonClicked(cartLine)

//            add_button.visibility = View.GONE
        }, CartLineListener { cartLine ->
            marketListViewModel.onRemoveButtonClicked(cartLine)
        })
        binding.productsList.adapter = productAdapter

//        marketListViewModel.cartLines.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                Log.i("i/MarketListFragment","Hubo algun cambio")
//                productAdapter.submitProductsList(it.toList())
//            }
//        })

        marketListViewModel.cartProducts.observe(viewLifecycleOwner, Observer {
            Log.i("i/MarketListFragment","Hubo algun cambio en prodsAmount: $it")
            productAdapter.submitProductsList(marketListViewModel.cartLines, binding.searchBar.query.toString())
        })
        productAdapter.submitProductsList(marketListViewModel.cartLines, searchBarText)
        Log.i("i/MarketListFragment", "Searchabr query: ${searchBarText}")

        binding.searchBar.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.i("i/MarketListFragment", "Query text changed")
                    Log.i("i/MarketListFragment", "searchbar text: ${newText}")
                    searchBarText = newText
                    if (newText.isNullOrEmpty()) {
                        binding.bannerList.visibility = View.VISIBLE
                    } else {
                        binding.bannerList.visibility = View.GONE
                        productAdapter.filter.filter(newText)
                    }
                    return false
                }
            })

            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    val imm =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    Log.i("i/MarketListFragment", "No tiene FOCO")
                } else {
                    Log.i("i/MarketListFragment", "Tiene FOCO")
                }
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.marketlist_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item,
//            requireView().findNavController())
//                || super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.cartCheckoutFragment ->
                this.findNavController().navigate(MarketListFragmentDirections.actionCartCheckout(marketListViewModel.cart))
        }
        return super.onOptionsItemSelected(item)
    }


}