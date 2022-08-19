package com.lis.player_java.tool

import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class ImageFun {
    fun setImage(imageView: ImageView?, image: Int) {
        Picasso.get().load(image).placeholder(image).into(imageView)
    }

    fun setImage(imageView: ImageView?, image: String?) {
        Picasso.get().load(image).into(imageView)
    }

    fun setImageToBackground(view: ImageView, image: Int) {
        Picasso.get()
            .load(image)
            .transform(BlurTransformation(view.context, 25, 5))
            .fit()
            .centerCrop()
            .into(view)
    }

    fun setImageToBackground(view: ImageView, image: String?) {
        Picasso.get()
            .load(image)
            .transform(BlurTransformation(view.context, 25, 5))
            .fit()
            .centerCrop()
            .into(view)
    }
}