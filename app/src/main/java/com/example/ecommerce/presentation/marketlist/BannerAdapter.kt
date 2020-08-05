package com.example.ecommerce.presentation.marketlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ListItemBannerBinding
import com.example.ecommerce.data.domain.Banner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BannerAdapter(/* */): ListAdapter<BannerItem, RecyclerView.ViewHolder>(
    BannerDiffCallback()
) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitBannerList(list: List<Banner>?) {
        adapterScope.launch {
            val items = list?.map {
                BannerItem.DataClass(
                    it
                )
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    private var viewHolderAmount = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewHolderAmount++
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val bannerItem = getItem(position) as BannerItem.DataClass
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

                return ViewHolder(
                    binding
                )
            }
        }
    }
}

class BannerDiffCallback : DiffUtil.ItemCallback<BannerItem>() {
    override fun areItemsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
        return oldItem == newItem
    }
}

sealed class BannerItem() {
    data class DataClass(val banner: Banner): BannerItem(){
        override val title = banner.title
    }

    abstract val title: String
}