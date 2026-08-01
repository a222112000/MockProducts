package com.alselwi.productsapp.presentation.navigation

sealed class Screen(val route: String) {
    data object ProductScreen: Screen("product_screen")
    data object ProductDetails: Screen("product_details/{productId}"){
        fun routeProduct(productIt: Long): String = "product_details/$productIt"
    }
}