package com.sinansarisen.mvvm_template.api


import com.sinansarisen.mvvm_template.models.LoginResponseModel
import com.sinansarisen.mvvm_template.models.LoginRequestModel
import retrofit2.Call
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(private val repository: ApiService) :
    ApiServiceInterfaceRepository {
    override  fun getLogin(loginRequestModel: LoginRequestModel): Call<LoginResponseModel> {
        return repository.login(loginRequestModel)
    }
}
