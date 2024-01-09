package com.sinansarisen.mvvm_template.models

data class LoginResponseModel(
    val code: String,
    val isSuccess: Boolean,
    val ssId: String
)