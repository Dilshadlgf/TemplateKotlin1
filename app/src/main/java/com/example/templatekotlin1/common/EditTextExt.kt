package com.example.templatekotlin1.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi


fun EditText.isNameValid():Boolean{
        return this.text.isNotEmpty() && this.text.length>2
    }

@Throws(Exception::class)
fun EditText.isInputValidWithThrows():EditText  {
    if(this.text.isNotEmpty() && this.text.length>2 ){
        return this
    }else{
        Toast.makeText(this.context,"Input valid data",Toast.LENGTH_SHORT).show()
        this.setHintTextColor(Color.RED)
        this.hint = "Please enter valid input here"
        throw Exception()
        return this
    }
}
fun EditText.isIntValueValid():Boolean{
    return try {
        this.text.isNotEmpty() && Integer.valueOf(this.text.toString())>0
    }catch (ex:Exception){
        false
    }


}
@RequiresApi(Build.VERSION_CODES.M)
fun EditText.setStyle(styleResId: Int) {
    val typedArray = context.theme.obtainStyledAttributes(styleResId, intArrayOf(android.R.attr.editTextStyle))
    val editTextStyleResId = typedArray.getResourceId(0, 0)
    typedArray.recycle()

    if (editTextStyleResId != 0) {
        setTextAppearance(editTextStyleResId)
    }
}