package com.example.ecommerce.common.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerce.R

@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
//            .apply(RequestOptions().placeholder("image placeholder"))
            .into(this)
    } ?: kotlin.run {
//        setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_broken_image_24))
        setImageResource(R.drawable.ic_baseline_broken_image_24)
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