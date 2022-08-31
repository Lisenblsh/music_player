package com.lis.player_java.tool

import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class ImageFun {
    fun setImage(imageView: ImageView, image: Any?) {
        if (image is Int) {
            Picasso.get().load(image).placeholder(image).into(imageView)
        } else if (image is String) {
            Picasso.get().load(image).into(imageView)
        }
    }

    fun setImageToBackground(view: ImageView, image: Any) {
        if (image is Int) {
            Picasso.get()
                .load(image)
                .transform(BlurTransformation(view.context, 5, 5))
                .fit()
                .centerCrop()
                .into(view)

        } else if (image is String) {
            Picasso.get()
                .load(image)
                .transform(BlurTransformation(view.context, 5, 5))
                .fit()
                .centerCrop()
                .into(view)
        }
    }
}