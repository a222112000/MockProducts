package com.alselwi.productsapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alselwi.productsapp.domain.entity.DomainHit

@Composable
fun ProductCard(
    hit: DomainHit
){
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp),
        elevation = CardDefaults.cardElevation(12.dp)) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(text = hit.price.toString())
        }
    }
}