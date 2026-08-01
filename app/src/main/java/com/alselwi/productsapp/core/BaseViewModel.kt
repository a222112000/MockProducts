package com.alselwi.productsapp.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<UiState,UiEffect,UiEvent>(initialize: UiState): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(initialize)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>(replay = 0, extraBufferCapacity = 1)
    val uiEffect: SharedFlow<UiEffect> = _uiEffect.asSharedFlow()

    abstract fun handleEvent(event: UiEvent)

    fun onEvent(event: UiEvent){
        handleEvent(event)
    }

    protected fun updateState(reducer: UiState.()->UiState){
        _uiState.update { it.reducer() }
    }
    protected suspend fun shareEffect(effect: UiEffect){
        _uiEffect.tryEmit(effect)
    }
}