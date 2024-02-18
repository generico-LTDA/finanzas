package com.soleel.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.home.HomeRoute


const val homeRoute = "home_route"

fun NavController.backToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = homeRoute,
        content = { HomeRoute() }
    )
}