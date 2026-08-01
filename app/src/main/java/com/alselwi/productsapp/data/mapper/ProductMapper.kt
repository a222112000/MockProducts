package com.alselwi.productsapp.data.mapper

import com.alselwi.productsapp.data.remote.model.ProductResponse
import com.alselwi.productsapp.domain.entity.DomainAvailableSize
import com.alselwi.productsapp.domain.entity.DomainFeaturedMedia
import com.alselwi.productsapp.domain.entity.DomainHit
import com.alselwi.productsapp.domain.entity.DomainMedia
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun toDomain(productResponse: ProductResponse): DomainProductResponse{
            return DomainProductResponse(
                hits = productResponse.hits.map { hit ->
                    DomainHit(
                        availableSizes = hit.availableSizes.map { size ->
                            DomainAvailableSize(
                                id = size.id,
                                inStock = size.inStock,
                                inventoryQuantity = size.inventoryQuantity,
                                price = size.price,
                                size = size.size,
                                sku = size.sku
                            )
                        },
                        colour = hit.colour,
                        compareAtPrice = hit.compareAtPrice,
                        description = hit.description.orEmpty(),
                        discountPercentage = hit.discountPercentage,
                        featuredMedia = hit.featuredMedia.let { media ->
                            DomainFeaturedMedia(
                                admin_graphql_api_id = media.admin_graphql_api_id,
                                alt = media.alt,
                                created_at = media.created_at,
                                height = media.height,
                                id = media.id,
                                position = media.position,
                                product_id = media.product_id,
                                src = media.src,
                                updated_at = media.updated_at,
                                variant_ids = media.variant_ids,
                                width = media.width,
                            )
                        },
                        fit = hit.fit,
                        gender = hit.gender.orEmpty(),
                        handle = hit.handle.orEmpty(),
                        id = hit.id,
                        inStock = hit.inStock,
                        labels = hit.labels.orEmpty(),
                        media = hit.media.map { media ->
                            DomainMedia(
                                admin_graphql_api_id = media.admin_graphql_api_id,
                                alt = media.alt,
                                created_at = media.created_at,
                                height = media.height,
                                id = media.id,
                                position = media.position,
                                product_id = media.product_id,
                                src = media.src,
                                updated_at = media.updated_at,
                                variant_ids = media.variant_ids,
                                width = media.width,
                            )
                        },
                        objectID = hit.objectID.orEmpty(),
                        price = hit.price,
                        sizeInStock = hit.sizeInStock.orEmpty(),
                        sku = hit.sku.orEmpty(),
                        title = hit.title.orEmpty(),
                        type = hit.type.orEmpty()
                    )
                }
            )
    }
}