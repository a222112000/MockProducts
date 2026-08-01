package com.alselwi.productsapp.domain.entity

data class DomainAvailableSize(
    val id: Long,
    val inStock: Boolean,
    val inventoryQuantity: Int,
    val price: Int,
    val size: String,
    val sku: String
)