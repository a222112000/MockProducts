package com.alselwi.productsapp.presentation.contract

import com.alselwi.productsapp.domain.entity.DomainHit
import javax.annotation.concurrent.Immutable

@Immutable
data class ProductUiState(
    val loading: Boolean = false,
    val products: List<DomainHit> = emptyList(),
    val error: String? = null
    )

sealed interface ProductUiEvent{
    data object LoadingData: ProductUiEvent
    data object Refresh: ProductUiEvent
}

sealed interface ProductUiEffect{
    data class Toast(
        val message: String
    ): ProductUiEffect
    data class Navigation(
        val route: String
    ): ProductUiEffect
}