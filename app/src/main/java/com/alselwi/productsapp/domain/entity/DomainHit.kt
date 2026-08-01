package com.alselwi.productsapp.domain.entity

data class DomainHit(
    val availableSizes: List<DomainAvailableSize>,
    val colour: String,
    val compareAtPrice: String?,
    val description: String,
    val discountPercentage: String?,
    val featuredMedia: DomainFeaturedMedia,
    val fit: String?,
    val gender: List<String>,
    val handle: String,
    val id: Long,
    val inStock: Boolean,
    val labels: List<String>,
    val media: List<DomainMedia>,
    val objectID: String,
    val price: Int,
    val sizeInStock: List<String>,
    val sku: String,
    val title: String,
    val type: String
)