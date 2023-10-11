package com.example.templatekotlin1.common.network

import com.google.gson.JsonObject
import okhttp3.MultipartBody

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun login(loginRequest: JsonObject) = retrofitService.login(loginRequest)

    suspend fun otpValidation(loginRequest: JsonObject) = retrofitService.otpValidation(loginRequest)

    suspend fun singleFileUpload(files: List<MultipartBody.Part>,path: String) = retrofitService.singleFileUpload(files,path)


    suspend fun onePathFilter(path: String,loginRequest: JsonObject) = retrofitService.onePathFilter(path,loginRequest)

    suspend fun onePathFilterWithPagination(path: String,loginRequest: JsonObject) = retrofitService.onePathFilterWithPagination(path,loginRequest)

    suspend fun onePathGet(path: String,id: String) = retrofitService.onePathGet(path,id)

    suspend fun onePathSave(path: String,jsonObject: JsonObject) = retrofitService.onePathSave(path,jsonObject)

    suspend fun onePathPut(path: String,jsonObject: JsonObject) = retrofitService.onePathPut(path,jsonObject)

    suspend fun twoPathPost(path: String,path2: String,jsonObject: JsonObject) = retrofitService.twoPathPost(path,path2,jsonObject)

    suspend fun twoPathPut(path: String,path2: String,jsonObject: JsonObject) = retrofitService.twoPathPut(path,path2,jsonObject)

    suspend fun threePathDelete(path: String,path2: String,path3: String,id: String) = retrofitService.threePathDelete(path,path2,path3,id)
    suspend fun threePathPut(path: String,path2: String,path3: String,id: String) = retrofitService.threePathPut(path,path2,path3,id)

    suspend fun multiFileUpload(path: String,file: List<MultipartBody.Part>?) = retrofitService.multiFileUpload(file,path)
    suspend fun singleFileUpload(path: String,file: List<MultipartBody.Part>?) = retrofitService.singleFileUpload(file,path)
}