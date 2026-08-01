package com.alselwi.productsapp.domain.repository

import com.alselwi.productsapp.core.AppError
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<NetworkResult<DomainProductResponse, AppError>>
}