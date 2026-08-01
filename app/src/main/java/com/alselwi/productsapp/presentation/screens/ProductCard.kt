package com.alselwi.productsapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alselwi.productsapp.domain.entity.DomainHit

@Composable
fun ProductCard(
    hit: DomainHit,
    onClick:(Long)-> Unit
){
    val aspectRatio = hit.featuredMedia.width.toFloat() /
            hit.featuredMedia.height.toFloat()
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp).clickable(
        onClick = { onClick(hit.id) }),
        elevation = CardDefaults.cardElevation(12.dp)) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(text = hit.price.toString())
            AsyncImage(
                model = hit.featuredMedia.src,
                contentDescription = hit.featuredMedia.alt,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                ,
                onSuccess = {

                },
                onError = { error ->
                    Log.e(
                        "recordsxxx",
                        "Image failed: ${hit.featuredMedia.src}",
                        error.result.throwable
                    )
                }
            )
        }
    }
}