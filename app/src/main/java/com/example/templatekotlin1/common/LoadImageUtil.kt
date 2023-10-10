package com.example.templatekotlin1.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.templatekotlin1.BuildConfig
import com.example.templatekotlin1.R

class LoadImageUtil {
    companion object{
        fun loadImage(imageView: ImageView,url:String){
            if(url==null || url.isEmpty()){
                imageView.setImageResource(android.R.drawable.ic_menu_camera)
                return
            }
            Glide.with(imageView.context).load(BuildConfig.BASE_URL+url).apply( RequestOptions()
                .placeholder(android.R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                // here you add some value , if the next time you add the same value then it will load from cache otherwise if you put new value you will download , then save in cache
            ).into(imageView)
        }

    }
}