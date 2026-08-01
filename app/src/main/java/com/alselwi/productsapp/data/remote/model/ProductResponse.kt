package com.alselwi.productsapp.data.remote.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProductResponse(
    val hits: List<Hit>
)