package com.alselwi.productsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alselwi.productsapp.presentation.screens.ProductScreen
import com.alselwi.productsapp.presentation.viewmodel.ProductViewModel

@Composable
fun AppHost(
    navHostController: NavHostController,
    startDestination: String = Screen.ProductScreen.route
){
    val viewModel: ProductViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = startDestination){
        composable(Screen.ProductScreen.route) {
            ProductScreen(navController = navHostController,
                viewModel = viewModel)
        }
    }
}