package com.example.ecommerce.marketlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ListItemBannerBinding
import com.example.ecommerce.domain.Banner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BannerAdapter(/* */): ListAdapter<DataItem, RecyclerView.ViewHolder>(BannerDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitBannerList(list: List<Banner>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.BannerItem(it)}
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    private var viewHolderAmount = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("i/BannerAdapter","ViewHolders Amount: $viewHolderAmount")
        viewHolderAmount++
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val bannerItem = getItem(position) as DataItem.BannerItem
                holder.bind(bannerItem.banner)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Banner) {
            binding.banner = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBannerBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class BannerDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {

    data class BannerItem(val banner: Banner) : DataItem() {
        override val id = banner.id
    }
    abstract val id: Int
}