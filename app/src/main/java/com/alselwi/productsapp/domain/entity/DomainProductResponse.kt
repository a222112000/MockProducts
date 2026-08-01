package com.alselwi.productsapp.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class DomainProductResponse(
    val hits: List<DomainHit>
)