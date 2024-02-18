package com.soleel.finanzas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.soleel.accounts.navigation.navigateToAccounts
import com.soleel.createtransaction.navigation.navigateToCreateTransaction
import com.soleel.finanzas.navigation.TopLevelDestination
import com.soleel.finanzas.navigation.TopLevelDestination.ACCOUNTS
import com.soleel.finanzas.navigation.TopLevelDestination.HOME
import com.soleel.finanzas.navigation.TopLevelDestination.PROFILE
import com.soleel.finanzas.navigation.TopLevelDestination.STATS
import com.soleel.home.navigation.homeRoute
import com.soleel.home.navigation.backToHome
import com.soleel.paymentaccountcreate.navigation.navigateToPaymentAccountCreateGraph
import com.soleel.profile.navigation.navigateToProfile
import com.soleel.stats.navigation.navigateToStats
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberFinanzasAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    showBottomBar: MutableState<Boolean> = remember { mutableStateOf(true) },
    showAddFloating: MutableState<Boolean> = remember { mutableStateOf(true) },
    showAddModal: MutableState<Boolean> = remember { mutableStateOf(false) },
    showCancelAlert: MutableState<Boolean> = remember { mutableStateOf(false) }
): FinanzasAppState {

    return remember(
        key1 = navController,
        key2 = coroutineScope,
//        calculation = ::createAppState
        calculation = {
            FinanzasAppState(
                navController = navController,
                coroutineScope = coroutineScope,
                showBottomBar = showBottomBar,
                showAddFloating = showAddFloating,
                showAddModal = showAddModal,
                showCancelAlert = showCancelAlert
            )
        }
    )
}

private fun createAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    showBottomBar: MutableState<Boolean>,
    showAddFloating: MutableState<Boolean>,
    showAddModal: MutableState<Boolean>,
    showCancelAlert: MutableState<Boolean>
): FinanzasAppState {
    return FinanzasAppState(
        navController = navController,
        coroutineScope = coroutineScope,
        showBottomBar = showBottomBar,
        showAddFloating = showAddFloating,
        showAddModal = showAddModal,
        showCancelAlert = showCancelAlert
    )
}

class FinanzasAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val showBottomBar: MutableState<Boolean>,
    val showAddFloating: MutableState<Boolean>,
    val showAddModal: MutableState<Boolean>,
    val showCancelAlert: MutableState<Boolean>,
) {

    @Composable
    fun getCurrentDestination(): NavDestination? {
        return navController.currentBackStackEntryAsState().value?.destination
    }

    @Composable
    fun getCurrentTopLevelDestination(): TopLevelDestination? {
        return when (getCurrentDestination()?.route) {
            homeRoute -> TopLevelDestination.HOME
            else -> null
        }
    }

    fun topLevelDestinations(): List<TopLevelDestination> {
        return TopLevelDestination.entries
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace(label = "Navigation: ${topLevelDestination.name}",
            block = {
                val topLevelNavOptions = navOptions(optionsBuilder = {
                    popUpTo(id = navController.graph.findStartDestination().id,
                        popUpToBuilder = { saveState = true })
                    launchSingleTop = true
                    restoreState = true
                })

                when (topLevelDestination) {
                    HOME -> navController.backToHome(topLevelNavOptions)

                    STATS -> navController.navigateToStats(topLevelNavOptions)

                    ACCOUNTS -> navController.navigateToAccounts(topLevelNavOptions)

                    PROFILE -> navController.navigateToProfile(topLevelNavOptions)
                }
            })
    }

    fun navigateToCreatePaymentAccount() {
        navController.navigateToPaymentAccountCreateGraph()
    }

    fun navigateToCreateTransaction() {
        navController.navigateToCreateTransaction()
    }

    fun backToHome() {
        navController.popBackStack(route = homeRoute, inclusive = false)
    }

    fun shouldShowBottomBar(): Boolean {
        return this.showBottomBar.value
    }

    fun showBottomBar() {
        this.showBottomBar.value = true
    }

    fun hideBottomBar() {
        this.showBottomBar.value = false
    }

    fun shouldShowAddFloating(): Boolean {
        return this.showAddFloating.value
    }

    fun showAddFloating() {
        this.showAddFloating.value = true
    }

    fun hideAddFloating() {
        this.showAddFloating.value = false
    }

    fun shouldShowAddModal(): Boolean {
        return this.showAddModal.value
    }

    fun showAddModal() {
        this.showAddModal.value = true
    }

    fun hideAddModal() {
        this.showAddModal.value = false
    }

    fun shouldShowCancelAlert(): Boolean {
        return this.showCancelAlert.value
    }

    fun showCancelAlert() {
        this.showCancelAlert.value = true
    }

    fun hideCancelAlert() {
        this.showCancelAlert.value = false
    }
}

