package com.alselwi.productsapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alselwi.productsapp.R
import com.alselwi.productsapp.domain.entity.DomainAvailableSize
import com.alselwi.productsapp.domain.entity.DomainHit

@Composable
fun ProductCard(
    hit: DomainHit,
    onClick: (Long) -> Unit
) {
    val aspectRatio =
        if (hit.featuredMedia.height > 0) {
            hit.featuredMedia.width.toFloat() /
                    hit.featuredMedia.height.toFloat()
        } else {
            0.8f
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onClick(hit.id)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = hit.featuredMedia.src,
                contentDescription =
                    hit.featuredMedia.alt ?: hit.title,
                placeholder = painterResource(
                    R.drawable.placeholder_image
                ),
                error = painterResource(
                    R.drawable.placeholder_image
                ),
                fallback = painterResource(
                    R.drawable.placeholder_image
                ),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio),
                onError = { error ->
                    Log.e(
                        "recordsxxx",
                        "Image failed: ${hit.featuredMedia.src}",
                        error.result.throwable
                    )
                }
            )

            Text(
                text = hit.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = hit.type,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Colour: ${hit.colour}",
                style = MaterialTheme.typography.bodyMedium
            )

            val genderText = hit.gender.joinToString(", ") { gender ->
                when (gender.lowercase()) {
                    "f" -> "Women"
                    "m" -> "Men"
                    else -> gender.uppercase()
                }
            }.ifBlank {
                "Not specified"
            }

            Text(
                text = "Gender: $genderText",
                style = MaterialTheme.typography.bodySmall
            )

            hit.fit?.takeIf { it.isNotBlank() }?.let { fit ->
                Text(
                    text = "Fit: $fit",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "£%.2f".format(hit.price / 100.0),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                hit.compareAtPrice
                    ?.toDoubleOrNull()
                    ?.let { comparePrice ->
                        Text(
                            text = "£%.2f".format(
                                comparePrice / 100.0
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
            }

            hit.discountPercentage
                ?.takeIf { it.isNotBlank() }
                ?.let { discount ->
                    Text(
                        text = "$discount% off",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

            Text(
                text = if (hit.inStock) {
                    "Product available"
                } else {
                    "Product out of stock"
                },
                color = if (hit.inStock) {
                    Color(0xFF2E7D32)
                } else {
                    Color.Red
                },
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "SKU: ${hit.sku}",
                style = MaterialTheme.typography.bodySmall
            )

            if (hit.labels.isNotEmpty()) {
                Text(
                    text = "Labels: ${hit.labels.joinToString()}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            HorizontalDivider()

            Text(
                text = "Available sizes",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            hit.availableSizes.forEach { availableSize ->
                ProductSizeItem(
                    availableSize = availableSize
                )
            }
        }
    }
}

@Composable
fun ProductSizeItem(
    availableSize: DomainAvailableSize
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Size: ${availableSize.size.uppercase()}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (availableSize.inStock) {
                    "Available"
                } else {
                    "Out of stock"
                },
                color = if (availableSize.inStock) {
                    Color(0xFF2E7D32)
                } else {
                    Color.Red
                },
                fontWeight = FontWeight.Bold
            )
        }

        if (availableSize.inStock) {
            Text(
                text = "Quantity: ${availableSize.inventoryQuantity}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Price: £%.2f".format(
                    availableSize.price / 100.0
                ),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "SKU: ${availableSize.sku}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}