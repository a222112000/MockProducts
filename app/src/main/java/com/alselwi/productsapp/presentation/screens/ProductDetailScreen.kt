package com.alselwi.productsapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.alselwi.productsapp.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Long,
                        viewModel : ProductViewModel,
                        onBackClick :()-> Unit){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val product = state.products.find { it.id == productId }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = product?.title.toString(), style =
            MaterialTheme.typography.headlineLarge) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            })
    }) { paddingValues ->
        when{
            state.loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            state.products.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
                    contentAlignment = Alignment.Center){
                    Text(text = "No Product Found", style = MaterialTheme.typography.bodyLarge)
                }
            }
            else -> {
                val imageRatio =
                    if (
                        product?.featuredMedia?.width != null &&
                        product.featuredMedia.height > 0
                    ) {
                        product.featuredMedia.width.toFloat() /
                                product.featuredMedia.height.toFloat()
                    } else {
                        0.8f
                    }
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)){
                    Column(modifier = Modifier.padding(12.dp)
                        .verticalScroll(rememberScrollState())) {
                        AsyncImage(
                            model = product?.featuredMedia?.src,
                            contentDescription =
                                product?.featuredMedia?.alt ?: product?.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(imageRatio),
                            contentScale = ContentScale.Inside
                        )
                        Text(text = product?.price.toString(),
                            style = MaterialTheme.typography.labelMedium)
                        Text(text = product?.colour.toString(),
                            style = MaterialTheme.typography.labelMedium)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        HtmlText(html = product?.description.orEmpty(),
                            modifier = Modifier.padding(top = 7.dp))
                    }
                }
            }
        }
    }
}