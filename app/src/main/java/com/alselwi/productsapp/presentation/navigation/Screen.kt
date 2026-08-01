package com.alselwi.productsapp.presentation.navigation

sealed class Screen(val route: String) {
    data object ProductScreen: Screen("product_screen")
}