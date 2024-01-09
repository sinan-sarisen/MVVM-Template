package com.sinansarisen.mvvm_template.api


import com.sinansarisen.mvvm_template.models.LoginResponseModel
import com.sinansarisen.mvvm_template.models.LoginRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<LoginResponseModel>

}