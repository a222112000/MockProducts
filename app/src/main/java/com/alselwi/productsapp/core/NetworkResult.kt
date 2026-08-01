package com.alselwi.productsapp.core

sealed interface NetworkResult<out S, out E> {
    data class Successful<S>(
        val data: S
    ): NetworkResult<S, Nothing>
    data class Error<E>(
        val error: E
    ): NetworkResult<Nothing,E>
    data object Loading: NetworkResult<Nothing, Nothing>
}