package com.example.ecommerce.marketlist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ecommerce.R
import com.example.ecommerce.domain.Banner

@BindingAdapter("bannerImage")
fun ImageView.setBannerImage(item: Banner?) {
    item?.let { setImageResource(R.drawable.banana_banner) }
}