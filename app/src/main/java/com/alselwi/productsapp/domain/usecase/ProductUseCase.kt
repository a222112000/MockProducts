package com.alselwi.productsapp.domain.usecase

import com.alselwi.productsapp.core.AppError
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import com.alselwi.productsapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    val repository: ProductRepository
) {
    operator fun invoke(): Flow<NetworkResult<DomainProductResponse, AppError>> {
       return repository.getProducts().map { result ->
            when(result){
                is NetworkResult.Error -> result
                NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Successful -> {
                    NetworkResult.Successful(result.data)
                }
            }
        }
    }
}