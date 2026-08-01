package com.alselwi.productsapp.data.remote.api

import com.alselwi.productsapp.core.common.Constants.PRODUCTS_ENDPOINT
import com.alselwi.productsapp.data.remote.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET(PRODUCTS_ENDPOINT)
    suspend fun getProducts(): Response<ProductResponse>
}