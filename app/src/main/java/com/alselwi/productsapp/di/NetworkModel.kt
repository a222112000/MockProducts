package com.alselwi.productsapp.di

import com.alselwi.productsapp.core.common.Constants.BASE_URL
import com.alselwi.productsapp.data.remote.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

    val okHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiCall(retrofit: Retrofit): ProductApi{
        return retrofit.create<ProductApi>(ProductApi::class.java)
    }
}