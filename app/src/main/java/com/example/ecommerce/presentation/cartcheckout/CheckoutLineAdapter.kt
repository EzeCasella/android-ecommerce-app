package com.example.ecommerce.presentation.cartcheckout

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.utils.setImage
import com.example.ecommerce.data.domain.CartLine
import kotlinx.android.synthetic.main.list_item_checkout_line.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CheckoutLineAdapter() :
    ListAdapter<CheckoutLineListItem, RecyclerView.ViewHolder>(
        CheckoutLineDiffCallback()
    ) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitCartlines(list: List<CartLine>) {
        val items = list.map {
            CheckoutLineListItem.CheckoutLine(
                it
            )
        }
        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val checkoutLine = getItem(position) as CheckoutLineListItem.CheckoutLine
                holder.bind(checkoutLine.cartLine)
            }
        }
    }

    class ViewHolder private constructor(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(cartLine: CartLine) {
            view.apply {
                product_name.text = cartLine.product.name
                product_price.text = resources.getString(R.string.display_price_2precision, (cartLine.product.price * cartLine.prodAmount.toBigDecimal()))
                product_units.text = resources.getQuantityString(R.plurals.units, cartLine.prodAmount, cartLine.prodAmount)
                product_image.setImage(cartLine.product.unitImgURL)
            }

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_checkout_line, parent, false)
                return ViewHolder(
                    view
                )
            }
        }
    }
}

class CheckoutLineDiffCallback : DiffUtil.ItemCallback<CheckoutLineListItem>() {
    override fun areItemsTheSame(
        oldItem: CheckoutLineListItem,
        newItem: CheckoutLineListItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CheckoutLineListItem,
        newItem: CheckoutLineListItem
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class CheckoutLineListItem {
    data class CheckoutLine(val cartLine: CartLine): CheckoutLineListItem(){
        override val id = cartLine.id
    }
    abstract val id: Int
}

class ItemDecorationCheckoutColumns(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val totalSpanCount = getTotalSpanCount(parent)

        outRect.top = if (!isInTheFirstRow(position, totalSpanCount)) spacing else 0
        outRect.right = if (isFirstInRow(position, totalSpanCount)) spacing else 0
        outRect.left = if (isLastInRow(position, totalSpanCount)) spacing else 0
    }

    private fun getTotalSpanCount(parent: RecyclerView): Int {
        val layoutManager = parent.layoutManager as GridLayoutManager
        return layoutManager.spanCount
    }

    private fun isInTheFirstRow(position: Int, spanCount: Int): Boolean {
        return position < spanCount
    }

    private fun isFirstInRow(position: Int, spanCount: Int): Boolean {
        return position % spanCount == 0
    }

    private fun isLastInRow(position: Int, spanCount: Int): Boolean {
        return isFirstInRow(position + 1, spanCount)
    }
}