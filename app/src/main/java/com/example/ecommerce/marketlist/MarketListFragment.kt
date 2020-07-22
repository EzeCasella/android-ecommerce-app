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

        val bannerLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        binding.bannerList.layoutManager = bannerLayoutManager

        val adapter = BannerAdapter()
        binding.bannerList.adapter = adapter

//        TODO("Add viewModel's banners listener submiting list")
        marketListViewModel.banners.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitBannerList(it)
            }
        })

        return binding.root
    }
}