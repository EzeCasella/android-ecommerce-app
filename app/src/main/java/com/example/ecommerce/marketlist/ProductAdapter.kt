package com.example.ecommerce.marketlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ListItemProductBinding
import com.example.ecommerce.domain.CartLine
import com.example.ecommerce.domain.Category
import com.example.ecommerce.domain.Product
import kotlinx.android.synthetic.main.list_item_category.view.*
import kotlinx.android.synthetic.main.list_item_product.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_CATEGORY = 0
private val ITEM_VIEW_TYPE_CARTLINE = 1

class CartLineAdapter(val addClickListener: CartLineListener, val removeClickListener: CartLineListener) :
    ListAdapter<CartLineListItem, RecyclerView.ViewHolder>(ProductDiffCallback()), Filterable {

    private var cartLinesList: MutableList<CartLine> = mutableListOf()
    private var cartLinesFullList: List<CartLine> = listOf()

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /*
    * Here is where the products list is grouped in categories
    *
    * */
    fun submitProductsList(list: List<CartLine>?, filterQuery: String) {
        if (filterQuery.isNullOrEmpty()) {
            adapterScope.launch {
                var items: List<CartLineListItem> = listOf()
                if (list != null) {
                    cartLinesList = list.toMutableList()
                    if (cartLinesFullList.isEmpty()) cartLinesFullList = list.toList()

                    var filteringList: List<CartLine>
                    var rest = cartLinesList.toMutableList()
                    var category: Category

                    while (rest.isNotEmpty()) {
                        category = rest.first().product.category
                        filteringList = rest.filter { it.product.category == category }
                        rest.removeIf {
                            filteringList.contains(it)
                        }
                        items = items + listOf(CartLineListItem.CategoryItem(category)) +
                                filteringList.map { CartLineListItem.CartLineItem(it) }
                    }
                    submitList(items)
                }
            }
        } else {
            filter.filter(filterQuery)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
//            ITEM_VIEW_TYPE_CATEGORY -> TextViewHolder
            ITEM_VIEW_TYPE_CARTLINE -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_CATEGORY -> TextViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val productItem = getItem(position) as CartLineListItem.CartLineItem
                holder.bind(addClickListener, removeClickListener,productItem.cartLine)
            }
            is TextViewHolder -> {
                val categoryItem = getItem(position) as CartLineListItem.CategoryItem
                holder.bind(categoryItem.category.name)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CartLineListItem.CategoryItem -> ITEM_VIEW_TYPE_CATEGORY
            is CartLineListItem.CartLineItem -> ITEM_VIEW_TYPE_CARTLINE
        }
    }

    class TextViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(string: String) {
            itemView.text.text = string.toLowerCase().capitalize()
        }
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_category, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(addClickListener: CartLineListener, removeClickListener: CartLineListener,item: CartLine) {
            binding.cartLine = item
            binding.addClickListener = addClickListener
            binding.removeClickListener = removeClickListener

            // TODO setear visibility de buttons
            Log.i("i/ProductAdapter","item amount: ${item.prodAmount}")
            if (item.prodAmount == 0) {
                binding.addButton.visibility = View.VISIBLE
                binding.amountSelector.visibility = View.INVISIBLE
            } else {
                binding.addButton.visibility = View.INVISIBLE
                binding.amountSelector.visibility = View.VISIBLE
            }

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

    override fun getFilter(): Filter {
        return cartLineFilter()
    }

    inner class cartLineFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<CartLine> = mutableListOf()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(cartLinesFullList)

            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()

                for (item in cartLinesFullList) {
                    if (item.product.name.toLowerCase().contains(filterPattern) ||
                            item.product.category.toString().toLowerCase().contains(filterPattern))
                        filteredList.add(item)
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            cartLinesList.clear()
            cartLinesList.addAll(results?.values as List<CartLine>)
            submitProductsList(cartLinesList,"")
        }

}


}

class ProductDiffCallback : DiffUtil.ItemCallback<CartLineListItem>() {
    override fun areItemsTheSame(oldItem: CartLineListItem, newItem: CartLineListItem): Boolean {
        Log.i("i/ProductAdapter","OldItem id: ${oldItem.id}, New item id: ${newItem.id}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartLineListItem, newItem: CartLineListItem): Boolean {
        Log.i("i/ProductAdapter","OldItem amount: ${oldItem.id}, New item amount: ${newItem.id}")
        return oldItem.prodAmount == newItem.prodAmount
    }
}

class CartLineListener(val clickListener: (cartLine: CartLine) -> Unit) {
    fun onClick(cartLine: CartLine) = clickListener(cartLine)
}

sealed class CartLineListItem {
    data class CartLineItem(val cartLine: CartLine) : CartLineListItem() {
        override val id = cartLine.id
        override val prodAmount = cartLine.prodAmount

    }

    data class CategoryItem(val category: Category) : CartLineListItem() {
        override val id = Int.MIN_VALUE
        override val prodAmount = Int.MIN_VALUE
    }

    abstract val id: Int
    abstract val prodAmount: Int
}