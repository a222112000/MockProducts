package com.alselwi.productsapp.core

import androidx.compose.runtime.Immutable

@Immutable
data class AppError(
    val code: Int? = null,
    val message: String
)