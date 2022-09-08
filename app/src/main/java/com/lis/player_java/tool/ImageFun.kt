package com.lis.player_java.tool

import android.widget.ImageView
import coil.load
import jp.wasabeef.transformers.coil.BlurTransformation

class ImageFun {
    fun setImage(imageView: ImageView, image: Any?) {
        imageView.load(image)
    }

    fun setImageToBackground(imageView: ImageView, image: Any) {
        imageView.load(image) {
            transformations(BlurTransformation(
                    imageView.context,
                    5,
                    5
                )
            )
        }
    }
}