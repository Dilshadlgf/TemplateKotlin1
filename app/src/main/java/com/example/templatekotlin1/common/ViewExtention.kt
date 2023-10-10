package com.example.templatekotlin1.common

import android.view.View
import android.view.ViewGroup

//fun String?.makeDouble():Double{
//    if (this.isNullOrEmpty()){
//        return 0.0
//    }
//    return this.toDouble()
//}
fun String.makeDouble():Double{
    if (this.isEmpty()){
        return 0.0
    }
    return this.toDouble()
}
fun String.showNAifStringEmpty():String{
    if(this.isEmpty()){
        return "N/A"
    }
    return this
}


