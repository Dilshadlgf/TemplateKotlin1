package com.example.templatekotlin1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.templatekotlin1.common.db.dao.UserDao
import com.example.templatekotlin1.ui.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(userDao.getTotalCount()>0){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        finish()
    }
}