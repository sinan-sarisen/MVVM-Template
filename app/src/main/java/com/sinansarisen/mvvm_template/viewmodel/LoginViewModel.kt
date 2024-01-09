package com.sinansarisen.mvvm_template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinansarisen.mvvm_template.models.LoginResponseModel
import com.sinansarisen.mvvm_template.api.ApiServiceInterfaceRepository
import com.sinansarisen.mvvm_template.models.LoginRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: ApiServiceInterfaceRepository) :
    ViewModel() {

    private val _login = MutableLiveData<LoginResponseModel>()
    val login: LiveData<LoginResponseModel>
        get() = _login


    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String>
        get() = _loginError


    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading


    private val _activeError = MutableLiveData<String?>()
    val activeError: LiveData<String?>
        get() = _activeError


    fun getLogin(loginRequestModel: LoginRequestModel) {
        viewModelScope.launch {
            repository.getLogin(loginRequestModel)
                .enqueue(object : retrofit2.Callback<LoginResponseModel> {
                    override fun onResponse(
                        call: Call<LoginResponseModel>,
                        response: Response<LoginResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            _login.value = response.body()
                            if(response.body()?.isSuccess == false && response.body()?.code.equals("100"))
                            {
                                _dataLoading.value = false
                                _activeError.value = "ClientApp.DeviceBanned"
                            }

                        } else {
                            _dataLoading.value = true
                            _loginError.value = response.code().toString()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                        _loginError.value = t.message.toString()
                        _dataLoading.value = false
                    }
                })
        }
    }


}