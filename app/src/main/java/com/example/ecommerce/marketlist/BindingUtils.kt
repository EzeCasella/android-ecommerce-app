package com.example.ecommerce.marketlist

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.domain.Banner

@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
//            .apply(RequestOptions().placeholder("image placeholder"))
            .into(this)
    }
}
//private var setImageCalls = 0
//
//@BindingAdapter("imageUrl")
//fun setImage(imgView: ImageView,imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//
//        setImageCalls++
//        Log.i("i/BindingUtils","SetImageCalls: $setImageCalls")
//        Glide.with(imgView.context)
//            .load(imgUri)
//            .into(imgView)
//    }
//}