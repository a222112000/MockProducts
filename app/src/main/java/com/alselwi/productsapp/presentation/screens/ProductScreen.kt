package com.alselwi.productsapp.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.alselwi.productsapp.presentation.contract.ProductUiEffect
import com.alselwi.productsapp.presentation.contract.ProductUiEvent
import com.alselwi.productsapp.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductScreen(
    navController: NavController,
    viewModel: ProductViewModel
){
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collectLatest { effect ->
            when(effect){
                is ProductUiEffect.Navigation -> navController.navigate(effect.route)
                is ProductUiEffect.Toast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.onEvent(ProductUiEvent.LoadingData)
    }
    ProductContents(loading = state.loading, hit = state.products, refreshClick = {
        viewModel.onEvent(ProductUiEvent.Refresh)
    })
}