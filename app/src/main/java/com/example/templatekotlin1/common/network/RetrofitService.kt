package com.example.templatekotlin1.common.network

import com.example.templatekotlin1.common.baseModel.RootResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @POST("user/auth/otplogin/generateotp")
    suspend fun login(@Body request: JsonObject) : Response<RootResponse>

    @POST("user/auth/otplogin/validateotp")
    suspend fun otpValidation(@Body request: JsonObject) : Response<RootResponse>




    @POST("{path1}/filter?pageno=no")
    suspend fun onePathFilter(@Path("path1") string: String, @Body loginRequest: JsonObject) : Response<RootResponse>

    @POST("{path1}/filter")
    suspend fun onePathFilterWithPagination(@Path("path1") string: String, @Body loginRequest: JsonObject) : Response<RootResponse>

    @POST("test")
    suspend fun test( @Body loginRequest: JsonObject) : Response<RootResponse>

    @POST("{path1}")
    suspend fun onePathSave( @Path("path1") string: String,@Body loginRequest: JsonObject) : Response<RootResponse>

    @PUT("{path1}")
    suspend fun onePathPut( @Path("path1") string: String,@Body loginRequest: JsonObject) : Response<RootResponse>

    @POST("{path1}/{path2}")
    suspend fun twoPathPost( @Path("path1") string: String,@Path("path2") path2: String,@Body loginRequest: JsonObject) : Response<RootResponse>

    @PUT("{path1}/{path2}")
    suspend fun twoPathPut( @Path("path1") string: String,@Path("path2") path2: String,@Body loginRequest: JsonObject) : Response<RootResponse>

    @DELETE("{path1}/{path2}/{path3}")
    suspend fun threePathDelete( @Path("path1") string: String,@Path("path2") path2: String,@Path("path3") path3: String,@Query("id") id: String) : Response<RootResponse>

    @PUT("{path1}/{path2}/{path3}")
    suspend fun threePathPut( @Path("path1") string: String,@Path("path2") path2: String,@Path("path3") path3: String,@Query("id") id: String) : Response<RootResponse>

    @GET("{path1}")
    suspend fun onePathGet(@Path("path1") string: String,@Query("id") id:String) : Response<RootResponse>


    @Multipart
    @POST("common/docupload/{path}")
    suspend fun singleFileUpload(
        @Part file: List<MultipartBody.Part>?,
        @Path("path") path: String?
    ): Response<RootResponse>

    @Multipart
    @POST("common/docsupload/{path}")
    suspend fun multiFileUpload(
        @Part file: List<MultipartBody.Part>?,@Path("path") path: String?
    ): Response<RootResponse>
}