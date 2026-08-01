package com.alselwi.productsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alselwi.productsapp.presentation.screens.ProductDetailScreen
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
        composable(Screen.ProductDetails.route, arguments = listOf(navArgument("productId"){
            type = NavType.LongType
        })) { backStack ->
            val productId = backStack.arguments?.getLong("productId") ?: return@composable
            ProductDetailScreen(productId = productId, viewModel = viewModel, onBackClick ={
                navHostController.popBackStack()
            })
        }
    }
}