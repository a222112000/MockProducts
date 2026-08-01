package com.alselwi.productsapp.data.remote.datasource

import com.alselwi.productsapp.core.AppError
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.data.remote.api.ProductApi
import com.alselwi.productsapp.data.remote.model.ProductResponse
import javax.inject.Inject

class ProductsDataSource @Inject constructor(
    val api: ProductApi
) {

    suspend fun getProducts(): NetworkResult<ProductResponse, AppError>{
         return try {
             val response = api.getProducts()
             if(response.isSuccessful){
                 val body = response.body()
                 if(body != null){
                     NetworkResult.Successful(body)
                 }else{
                     NetworkResult.Error(AppError(
                         code = response.code(),
                         message = response.message()
                     ))
                 }
             }else{
                 NetworkResult.Error(AppError(
                     message = response.message()
                 ))
             }
         }catch (e: Exception){
             NetworkResult.Error(AppError(
                 message = e.localizedMessage?: "Unknown Error"
             ))
         }
    }
}