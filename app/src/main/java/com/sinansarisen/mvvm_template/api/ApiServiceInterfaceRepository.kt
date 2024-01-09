package com.sinansarisen.mvvm_template.api


import com.sinansarisen.mvvm_template.models.LoginResponseModel
import com.sinansarisen.mvvm_template.models.LoginRequestModel
import retrofit2.Call

interface ApiServiceInterfaceRepository {

    fun getLogin(loginRequestModel: LoginRequestModel): Call<LoginResponseModel>
}