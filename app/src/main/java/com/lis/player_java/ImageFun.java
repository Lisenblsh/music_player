package com.lis.player_java;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageFun {
    public void setImage(ImageView imageView, int image){
        Picasso.get().load(image).placeholder(image).into(imageView);
    }
}
