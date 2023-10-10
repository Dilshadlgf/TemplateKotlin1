package com.example.templatekotlin1.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner


class CustomSpinner : AppCompatSpinner {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(context)
    }
    constructor(context: Context,  attrs:AttributeSet):super(context, attrs){
        initView(context)
    }

    private fun initView(context: Context) {

    }


    fun getSelectedItemCustomId():String{
        if(adapter is CustomSpinnerAdaptor) {
            return (adapter as CustomSpinnerAdaptor).getCustomId(selectedItemPosition)
        }
        return ""
    }


}
class CustomSpinnerAdaptor(val context: Context, var dataSource: List<List<String>>) : BaseAdapter() {

private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=getCustomView(position, convertView, parent)
        val textView=view.findViewById<TextView>(android.R.id.text1)

        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position][0];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }
    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?):View{
        val view: View
        if (convertView == null) {
            view = inflater.inflate(android.R.layout.test_list_item, parent, false)
        } else {
            view = convertView
        }

        val textView=view.findViewById<TextView>(android.R.id.text1)
        textView.text=dataSource.get(position)[0]
        if(dataSource.get(position).size>1){
            view.tag = dataSource.get(position)[1]
        }
        return view
    }
    fun getCustomId(position: Int):String{
        if(dataSource.size>1) {
            return dataSource[position][1]
        }
        return ""
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(android.R.id.text1) as TextView
//        val img: ImageView

        init {
            //            img = row?.findViewById(R.id.img) as ImageView
        }
    }

}