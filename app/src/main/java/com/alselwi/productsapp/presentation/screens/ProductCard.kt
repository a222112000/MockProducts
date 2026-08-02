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
import androidx.compose.ui.Alignment
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
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick(hit.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            AsyncImage(
                model = hit.featuredMedia.src,
                contentDescription = hit.featuredMedia.alt ?: hit.title,
                placeholder = painterResource(R.drawable.placeholder_image),
                error = painterResource(R.drawable.placeholder_image),
                fallback = painterResource(R.drawable.placeholder_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = hit.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )

            Text(
                text = hit.colour,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
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
                            text = "£%.2f".format(comparePrice / 100.0),
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
            }

            hit.discountPercentage?.takeIf { it.isNotBlank() }?.let {

                Text(
                    text = "$it% OFF",
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = if (hit.inStock) "In Stock" else "Out of Stock",
                color = if (hit.inStock)
                    Color(0xFF2E7D32)
                else
                    Color.Red,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )

            if (hit.labels.isNotEmpty()) {
                Text(
                    text = hit.labels.joinToString(" • "),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            hit.availableSizes.forEach { availableSize -> ProductSizeItem( availableSize = availableSize ) }
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
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}