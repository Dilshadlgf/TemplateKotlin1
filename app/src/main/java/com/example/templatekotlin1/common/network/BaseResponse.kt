package com.example.templatekotlin1.common.network

sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
    data class Error(val msg: String?) : BaseResponse<Nothing>()
    data class Close(val msg: String?) : BaseResponse<Nothing>()
    data class Update(val name:String,val id: Int,val action:Int,val isSuccess:Boolean) : BaseResponse<Nothing>()
    data class Filter<out T>(val name:String,val id: Int,val action:Int,val data: T? = null) : BaseResponse<T>()
}
