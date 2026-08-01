package com.alselwi.productsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alselwi.productsapp.core.BaseViewModel
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.domain.usecase.ProductUseCase
import com.alselwi.productsapp.presentation.contract.ProductUiEffect
import com.alselwi.productsapp.presentation.contract.ProductUiEvent
import com.alselwi.productsapp.presentation.contract.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductUseCase: ProductUseCase):
BaseViewModel<ProductUiState, ProductUiEffect, ProductUiEvent>(ProductUiState()){

    private var jobs: Job? = null

    override fun handleEvent(event: ProductUiEvent) {
        when(event){
            ProductUiEvent.LoadingData -> loadData()
            ProductUiEvent.Refresh -> loadData()
        }
    }

    private fun loadData(){
        jobs?.cancel()
        jobs = viewModelScope.launch {
            getProductUseCase().collect { result ->
                when(result){
                    is NetworkResult.Error -> {
                        updateState { copy(loading = false, error = result.error.message) }
                        shareEffect(ProductUiEffect.Toast(result.error.message))
                    }
                    NetworkResult.Loading -> {
                        updateState { copy(loading = true, error = null) }
                    }
                    is NetworkResult.Successful -> {
                        updateState { copy(loading = false, error = null, products = result.data.hits) }
                    }
                }
            }
        }
    }
}