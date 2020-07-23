package com.example.ecommerce.marketlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ListItemProductBinding
import com.example.ecommerce.domain.Category
import com.example.ecommerce.domain.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter() :
    ListAdapter<ProductListItem, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /*
    * Here is where the products list is grouped in categories
    * TODO("Agrupado en categorias")
    * */
    fun submitProductsList(list: List<Product>?) {
        adapterScope.launch {
            val items = list?.map { ProductListItem.ProductItem(it) }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val productItem = getItem(position) as ProductListItem.ProductItem
                holder.bind(productItem.product)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductListItem>() {
    override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem == newItem
    }
}

sealed class ProductListItem {
    data class ProductItem(val product: Product) : ProductListItem() {
        override val id = product.id
    }

    data class CategoryItem(val category: Category) : ProductListItem() {
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int
}