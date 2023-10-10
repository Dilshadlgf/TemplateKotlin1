package com.example.templatekotlin1.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.templatekotlin1.R

class LoadingDialog {

    companion object{
        fun initLoadingDialog(context: Context): AlertDialog {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setCancelable(false) // if you want user to wait for some process to finish,

            builder.setView(R.layout.layout_loading_dialog)
            val dialog: AlertDialog = builder.create()
            return dialog
        }
    }

}