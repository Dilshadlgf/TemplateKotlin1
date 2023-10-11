package com.example.templatekotlin1.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templatekotlin1.common.MyJsonUtil
import com.example.templatekotlin1.common.baseModel.RootResponse
import com.example.templatekotlin1.common.baseModel.User
import com.example.templatekotlin1.common.network.BaseResponse
import com.example.templatekotlin1.common.network.MainRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel() {


    private val _login = MutableLiveData<BaseResponse<Any>>()
    val loginLD: LiveData<BaseResponse<Any>> = _login

    private val _otp = MutableLiveData<BaseResponse<Any>>()
    val otpLD: LiveData<BaseResponse<Any>> = _otp

    fun login(jsonObject: JsonObject){
        _login.value= BaseResponse.Loading()
        viewModelScope.launch {

            try {

                val response = mainRepository.login( jsonObject)
                if (response.code()==200) {

//                    val list= response.body()?.let { parseData(it) }
                    _login.value= BaseResponse.Success(true)
                } else {
                    _login.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                _login.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun validDateOtp(jsonObject: JsonObject){
        _otp.value= BaseResponse.Loading()
        viewModelScope.launch {

            try {

                val response = mainRepository.otpValidation( jsonObject)
                if (response.code()==200) {

                    val list= response.body()?.let { parseData(it) }
                    _otp.value= BaseResponse.Success(list)
                } else {
                    _otp.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                _otp.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun parseData(rootResponse: RootResponse):User{
        val mlist= MyJsonUtil.getDataPojoFromDataJson(rootResponse.response.data.user, User::class.java)
        return mlist as User
    }
}