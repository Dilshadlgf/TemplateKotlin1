package com.example.templatekotlin1.common

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalenderTextView : AppCompatTextView {
    lateinit var  StartTime:DatePickerDialog
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initCalender(context)
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {

    }
    constructor(context: Context):super(context){
        initCalender(context)
    }
    fun removeDefaultDate(){
        text=""
    }
    private fun initCalender(context: Context){
        text = DateTimeUtil.getCurrentDate()
        val newCalendar = Calendar.getInstance()
        StartTime = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val myFormat = "dd-MM-yyyy"
                val dateFormat = SimpleDateFormat(myFormat, Locale.US)
                text = dateFormat.format(newDate.time)
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
//        setOnTouchListener(object :OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                StartTime.show()
//                return false
//            }
//
//        })
        setOnClickListener {
            StartTime.show()
        }
    }
    fun setDefaultDate(string: String){
        text = string
    }
    fun getServerFormatDate():String{
       return DateTimeUtil.convertDateTimeToServerFormat(text.toString())
    }
    fun convertNormalFormat(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            if(text.isNotEmpty()){
                text = DateTimeUtil.getDateOnly(text.toString())
            }
        }

    }


}