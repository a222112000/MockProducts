package com.alselwi.productsapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.alselwi.productsapp.R
import com.alselwi.productsapp.domain.entity.DomainAvailableSize
import com.alselwi.productsapp.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Long,
    viewModel: ProductViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val product = state.products.find { it.id == productId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product?.title.orEmpty(),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when {
            state.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            product == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Product Found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                val imageRatio =
                    if (product.featuredMedia.height > 0) {
                        product.featuredMedia.width.toFloat() /
                                product.featuredMedia.height.toFloat()
                    } else {
                        0.8f
                    }

                val genderText =
                    product.gender.joinToString(", ") { gender ->
                        when (gender.lowercase()) {
                            "f" -> "Women"
                            "m" -> "Men"
                            else -> gender.uppercase()
                        }
                    }.ifBlank {
                        "Not specified"
                    }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = product.featuredMedia.src,
                        contentDescription =
                            product.featuredMedia.alt ?: product.title,
                        placeholder = painterResource(
                            R.drawable.placeholder_image
                        ),
                        error = painterResource(
                            R.drawable.placeholder_image
                        ),
                        fallback = painterResource(
                            R.drawable.placeholder_image
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(imageRatio),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = product.type,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "£%.2f".format(product.price / 100.0),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        product.compareAtPrice
                            ?.toDoubleOrNull()
                            ?.let { previousPrice ->
                                Text(
                                    text = "£%.2f".format(
                                        previousPrice / 100.0
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant,
                                    textDecoration =
                                        TextDecoration.LineThrough
                                )
                            }
                    }

                    product.discountPercentage
                        ?.takeIf { it.isNotBlank() }
                        ?.let { discount ->
                            Text(
                                text = "$discount% off",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    ProductDetailRow(
                        label = "Colour",
                        value = product.colour
                    )

                    ProductDetailRow(
                        label = "Gender",
                        value = genderText
                    )

                    ProductDetailRow(
                        label = "Fit",
                        value = product.fit ?: "Not specified"
                    )

                    ProductDetailRow(
                        label = "SKU",
                        value = product.sku
                    )

                    ProductDetailRow(
                        label = "Product ID",
                        value = product.id.toString()
                    )

                    Text(
                        text = if (product.inStock) {
                            "Product available"
                        } else {
                            "Product out of stock"
                        },
                        color = if (product.inStock) {
                            Color(0xFF2E7D32)
                        } else {
                            Color.Red
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    if (product.labels.isNotEmpty()) {
                        HorizontalDivider()

                        Text(
                            text = "Labels",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        product.labels.forEach { label ->
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    HorizontalDivider()

                    Text(
                        text = "Sizes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    product.availableSizes.forEach { availableSize ->
                        ProductDetailSizeItem(
                            availableSize = availableSize
                        )
                    }

                    if (product.sizeInStock.isNotEmpty()) {
                        Text(
                            text = "Sizes in stock: ${
                                product.sizeInStock.joinToString(", ") {
                                    it.uppercase()
                                }
                            }",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    HorizontalDivider()

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HtmlText(
                        html = product.description,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ProductDetailSizeItem(
    availableSize: DomainAvailableSize
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Size: ${availableSize.size.uppercase()}",
                style = MaterialTheme.typography.bodyLarge,
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