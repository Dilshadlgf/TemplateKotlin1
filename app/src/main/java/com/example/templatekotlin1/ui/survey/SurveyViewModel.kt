package com.example.templatekotlin1.ui.survey

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
class SurveyViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _vendorListResult = MutableLiveData<BaseResponse<Any>>()
    val vendorListResult: LiveData<BaseResponse<Any>> = _vendorListResult

    fun getAllVendor(string: String,jsonObject: JsonObject) {

        _vendorListResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val response = repository.onePathFilter(string, jsonObject)
                if (response.code()==200) {

                    val list= response.body()?.let { parseData(it) }
                    _vendorListResult.value=BaseResponse.Success(list)
                } else {
                    _vendorListResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                _vendorListResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun parseData(rootResponse: RootResponse): User {
        val mlist= MyJsonUtil.getDataPojoFromDataJson(rootResponse.response.data.user, User::class.java)
        return mlist as User
    }
}