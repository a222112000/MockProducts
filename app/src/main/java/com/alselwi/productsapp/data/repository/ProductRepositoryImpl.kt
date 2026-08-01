package com.alselwi.productsapp.data.repository

import com.alselwi.productsapp.core.AppError
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.data.mapper.ProductMapper
import com.alselwi.productsapp.data.remote.datasource.ProductsDataSource
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import com.alselwi.productsapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val productMapper: ProductMapper,
    val productsDataSource: ProductsDataSource
) : ProductRepository{
    override fun getProducts(): Flow<NetworkResult<DomainProductResponse, AppError>> = flow {

        emit(NetworkResult.Loading)
        when(val result = productsDataSource.getProducts()){
            is NetworkResult.Error -> {
                emit(NetworkResult.Error(AppError(
                    message = result.error.message,
                    code = result.error.code
                )))
            }
            NetworkResult.Loading -> emit(NetworkResult.Loading)
            is NetworkResult.Successful -> {
                val map = productMapper.toDomain(result.data)
                emit(NetworkResult.Successful(map))
            }
        }
    }
}