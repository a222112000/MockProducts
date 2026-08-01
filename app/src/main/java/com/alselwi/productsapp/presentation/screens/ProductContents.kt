package com.alselwi.productsapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alselwi.productsapp.domain.entity.DomainHit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductContents(
    loading: Boolean,
    hit: List<DomainHit>,
    refreshClick:()-> Unit
){
    Scaffold(topBar = { TopAppBar(title = { Text("Products",
        style = MaterialTheme.typography.headlineLarge) },
        actions = {
            TextButton(onClick = refreshClick) {
                Text(text = "Refresh")
            }
        }) }) { paddingValues ->

        when{
            loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            hit.isEmpty() ->{
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
                    contentAlignment = Alignment.Center){
                    Text(text = "No Products available")
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    items(items = hit, key = {product->
                        product.id
                    }){ hit->
                        ProductCard(hit = hit)
                    }
                }
            }
        }
    }

}