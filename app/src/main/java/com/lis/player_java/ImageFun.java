package com.lis.player_java;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ImageFun {
    public void setImage(ImageView imageView, int image) {
        Picasso.get().load(image).placeholder(image).into(imageView);
    }

    public void setImage(ImageView imageView, String image) {
        Picasso.get().load(image).into(imageView);
    }

    public void setImageToBackground(ImageView view, int image) {
        Picasso.get()
                .load(image)
                .transform(new BlurTransformation(view.getContext(), 25, 5))
                .fit()
                .centerCrop()
                .into(view);

    }

    public void setImageToBackground(ImageView view, String image) {
        Picasso.get()
                .load(image)
                .transform(new BlurTransformation(view.getContext(), 25, 5))
                .fit()
                .centerCrop()
                .into(view);

    }
}
