package com.soleel.accounts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.accounts.AccountsRoute

const val accountsRoute = "accounts_route"

fun NavController.navigateToAccounts(navOptions: NavOptions? = null) {
    this.navigate(accountsRoute, navOptions)
}

fun NavGraphBuilder.accountsScreen() {
    composable(
        route = accountsRoute,
        content = { AccountsRoute() }
    )
}