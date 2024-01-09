package com.sinansarisen.mvvm_template.di


import com.sinansarisen.mvvm_template.api.ApiService
import com.sinansarisen.mvvm_template.api.ApiServiceInterfaceRepository
import com.sinansarisen.mvvm_template.api.ApiServiceRepository
import com.sinansarisen.mvvm_template.utils.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun injectRetrofitAPI(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(log().build())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun injectNormalRepo(api:ApiService) = ApiServiceRepository(api) as ApiServiceInterfaceRepository

    @Provides
    @Singleton
    fun log ():OkHttpClient.Builder{
        val logging: HttpLoggingInterceptor =
            HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        return httpClient
    }
}