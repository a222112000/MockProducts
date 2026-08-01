package com.alselwi.productsapp.data.remote.model

data class Hit(
    val availableSizes: List<AvailableSize>,
    val colour: String,
    val compareAtPrice: String?,
    val description: String,
    val discountPercentage: String?,
    val featuredMedia: FeaturedMedia,
    val fit: String?,
    val gender: List<String>,
    val handle: String,
    val id: Long,
    val inStock: Boolean,
    val labels: List<String>,
    val media: List<Media>,
    val objectID: String,
    val price: Double,
    val sizeInStock: List<String>,
    val sku: String,
    val title: String,
    val type: String
)