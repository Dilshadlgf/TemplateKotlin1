package com.example.templatekotlin1.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(appCompatActivity:  AppCompatActivity, var flist: List<Fragment>) :
    FragmentStateAdapter(appCompatActivity) {

    override fun getItemCount(): Int {
        return flist.size
    }

    override fun createFragment(position: Int): Fragment {

        return flist.get(position)
    }

}
