package com.example.templatekotlin1.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.templatekotlin1.BuildConfig
import com.example.templatekotlin1.R
import com.example.templatekotlin1.common.CustomSpinnerAdaptor


object ViewUtil {
        fun setEmptyTextView(root: ViewGroup){
           val tv= TextView(root.context)
            tv.text="No data found"
            tv.gravity=Gravity.CENTER
            tv.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            root.removeAllViews()
            root.addView(tv)
        }
    fun setEmptyTextViewTwo(root: ViewGroup){
           val tv= TextView(root.context)
            tv.text="No data found"
            tv.gravity=Gravity.CENTER
            tv.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
//            root.removeAllViews()
            root.addView(tv)
        }
    fun setFormattedDateInTextView(textView: TextView,date:String){
        val fdate=DateTimeUtil.getDateOnly(date)
        textView.text=fdate
        textView.setTag(android.R.string.ok,date)
    }

    fun deleteItem(context:Context,onClickListener: DialogInterface.OnClickListener): Dialog {
        val dialog= AlertDialog.Builder(context).setMessage("Do You Want to Delete").
        setPositiveButton("OK"
        ) { dialog, which -> onClickListener.onClick(dialog, which) }.
        setNegativeButton("Cancel"
        ) { dialog, which -> dialog?.dismiss() }.create();
        dialog.show()
        return dialog
    }
    fun showAlertDialog(context:Context,msg:String,onClickListener: DialogInterface.OnClickListener): Dialog {
        val dialog= AlertDialog.Builder(context).setMessage(msg).
        setPositiveButton("OK"
        ) { dialog, which -> onClickListener.onClick(dialog, which) }.
        setNegativeButton("Cancel"
        ) { dialog, which -> dialog?.dismiss() }.create();
        dialog.show()
        return dialog
    }
    fun getGenderList():List<List<String>>{
        val m= mutableListOf<List<String>>()
        m.add(listOf("Male","male"))
        m.add(listOf("Female","female"))
        return m
    }
    fun makeGenderSpinner(spinner: Spinner){
        val adaptor= CustomSpinnerAdaptor(spinner.context, getGenderList())
        spinner.adapter=adaptor
    }
    fun getModeList():List<List<String>>{
        val m= mutableListOf<List<String>>()
        m.add(listOf("DD","dd"))
        m.add(listOf("Cheque","cheque"))
        m.add(listOf("Online","online"))
        return m
    }
    fun makeModeSpinner(spinner: Spinner){
        val adaptor= CustomSpinnerAdaptor(spinner.context, getModeList())
        spinner.adapter=adaptor
    }
    fun getDepartmentList():List<List<String>>{
        val m= mutableListOf<List<String>>()
        m.add(listOf("Municipal","municipal"))
        m.add(listOf("Agriculture","agriculture"))
        return m
    }
    fun makeDepartmentSpinner(spinner: Spinner){
        val adaptor= CustomSpinnerAdaptor(spinner.context, getDepartmentList())
        spinner.adapter=adaptor
    }
    fun showImageInDialog(context:Context,url:Any,onClickListener: DialogInterface.OnClickListener?): Dialog {
        val imageView=ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)
        if(url is String) {
            Glide.with(imageView.context).load(BuildConfig.BASE_URL + url).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_menu_camera)

//                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                // here you add some value , if the next time you add the same value then it will load from cache otherwise if you put new value you will download , then save in cache
            ).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                   imageView.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        }else if(url is Uri){
            imageView.setImageURI(url)
        }

        val dialog= AlertDialog.Builder(context).setView(imageView).
        setPositiveButton("OK"
        ) { dialog, which -> onClickListener.let {
            dialog.dismiss()
            it?.onClick(dialog, which)
        }}
//            .
//        setNegativeButton("Cancel"
//        ) { dialog, which -> dialog?.dismiss() }
            .create();
        dialog.show()
        return dialog
    }
}