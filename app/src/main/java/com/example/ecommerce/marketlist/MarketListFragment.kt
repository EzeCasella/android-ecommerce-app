package com.example.ecommerce.marketlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.MarketListFragmentBinding

class MarketListFragment : Fragment() {

    companion object {
        fun newInstance() = MarketListFragment()
    }

    private lateinit var viewModel: MarketListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: MarketListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.market_list_fragment, container, false
        )

        val viewModelFactory = MarketListViewModelFactory()

        val marketListViewModel = ViewModelProvider(this, viewModelFactory).get(MarketListViewModel::class.java)

        binding.marketListViewModel = marketListViewModel

        binding.lifecycleOwner = this

        /*
        * Banners list setup
        * */
        val bannerLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.bannerList.layoutManager = bannerLayoutManager

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

        val productAdapter =  ProductAdapter()
        binding.productsList.adapter = productAdapter

        marketListViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productAdapter.submitProductsList(it)
            }
        })


        return binding.root
    }
}