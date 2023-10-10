package com.example.templatekotlin1.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gmc_2.util.FileUtil
import com.example.templatekotlin1.common.MyJsonUtil
import com.example.templatekotlin1.common.baseModel.RootResponse
import com.example.templatekotlin1.common.network.BaseResponse
import com.example.templatekotlin1.common.network.MainRepository
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class BaseViewModel @Inject constructor(
    application: Application,
    val mainRepository: MainRepository
) : AndroidViewModel(application) {

    private val _fileUpload: MutableLiveData<BaseResponse<Any>> = MutableLiveData()
    val fileUploadLiveData: LiveData<BaseResponse<Any>> = _fileUpload

    fun singleFileUpload(
        bytes: ByteArray,
        path: String
    ) {

        _fileUpload.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val arr: ByteArray = FileUtil.compressImageByteArray(bytes)
                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                val compressbody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), arr)
                builder.addFormDataPart("uploadfile", "file_", compressbody)
                val requestBody: List<MultipartBody.Part> = builder.build().parts

                val response = mainRepository.singleFileUpload(requestBody, path)
                if (response.code() == 200) {
                    val url= response.body()?.let { parseData(it)}
                    _fileUpload.value = BaseResponse.Success(url)
                } else {
                    _fileUpload.value = BaseResponse.Error(response?.message())
                }


            } catch (ex: Exception) {
                _fileUpload.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun parseData(rootResponse: RootResponse):String{
        val str= MyJsonUtil.getJsonFromPojoObj(rootResponse.response.data)
        return JsonParser().parse(str).getAsJsonObject().get("uri").asString
    }
}