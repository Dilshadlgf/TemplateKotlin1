package com.example.templatekotlin1.ui.activity.login

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.templatekotlin1.MainActivity
import com.example.templatekotlin1.R
import com.example.templatekotlin1.base.BaseActivity
import com.example.templatekotlin1.common.baseModel.User
import com.example.templatekotlin1.common.db.dao.UserDao
import com.example.templatekotlin1.common.network.BaseResponse
import com.example.templatekotlin1.databinding.ActivityLoginBinding
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity :BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel:LoginViewModel by viewModels()

    @Inject
    lateinit var userDao: UserDao

    override fun onInitialized() {

    }

    override fun setUpClicks() {
        binding.btnGetOtp.setOnClickListener {
            genrateOtp()
        }
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    override fun addObservers() {
        super.addObservers()
        loginViewModel.loginLD.observe(this, Observer {
            when(it){
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    binding.btnLogin.visibility= View.VISIBLE
                    binding.layEdOtp.visibility= View.VISIBLE
                    binding.btnGetOtp.isEnabled= false
                    binding.edmobileno1.isEnabled= false
                }

                is BaseResponse.Error -> {
                    stopLoading()
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }

            }
        })
        loginViewModel.otpLD.observe(this, Observer {
            when(it){
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    userDao.insertAgent(it.data as User)
                    openFragmentActivity()
                }

                is BaseResponse.Error -> {
                    stopLoading()
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }

            }
        })
    }
    private fun openFragmentActivity(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun genrateOtp(){
        if(binding.edmobileno1.text?.length==10) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("userName", binding.edmobileno1.text.toString())
            jsonObject.addProperty("usertype", "GarbageCollector")
            loginViewModel.login(jsonObject)
        }else{
            showToast("Enter valid no")
        }
    }
    private fun doLogin(){
        if(binding.edOtp.text?.length==4) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("mobile", binding.edmobileno1.text.toString())
            jsonObject.addProperty("usertype", "GarbageCollector")
            jsonObject.addProperty("otp", binding.edOtp.text.toString())
            loginViewModel.validDateOtp(jsonObject)
        }else{
            showToast("Enter valid otp")
        }
    }
}