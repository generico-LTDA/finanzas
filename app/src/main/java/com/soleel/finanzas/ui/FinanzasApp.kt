package com.soleel.finanzas.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.add.AddModalBottomSheet
import com.soleel.cancelalert.CancelAlertDialog
import com.soleel.finanzas.navigation.FinanzasNavHost
import com.soleel.finanzas.navigation.TopLevelDestination


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinanzasApp(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                FinanzasBottomBar(
                    destinations = appState.topLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.getCurrentDestination(),
                )
            }
        },
        floatingActionButton = {
            if (appState.shouldShowAddFloating())
                AddFloatingActionButton(onClick = { appState.showAddModal() })
        },
        content = {
            if (appState.shouldShowAddModal()) {
                AddModalBottomSheet(
                    onCreatePaymentAccount = appState::navigateToCreatePaymentAccount,
                    onCreateTransaction = appState::navigateToCreateTransaction,
                    onHideBottomBar = appState::hideBottomBar,
                    onHideAddFloating = appState::hideAddFloating,
                    onDismiss = appState::hideAddModal,
                    sheetState = bottomSheetState
                )
            }

            if (appState.shouldShowCancelAlert()) {
                CancelAlertDialog(
                    onShowBottomBar = appState::showBottomBar,
                    onShowAddFloating = appState::showAddFloating,
                    onConfirmation = appState::navigateToBack,
                    onDismissRequest = appState::hideCancelAlert,
                    dialogTitle = "¿Quieres volver al inicio?",
                    dialogText = "Cancelaras la creacion actual."
                )
            }

            FinanzasNavHost(appState = appState)
        }
    )
}

@Composable
private fun AddFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        content = {
            Icon(Icons.Filled.Add, "Add button.")
        }
    )
}

@Composable
private fun FinanzasBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        content = {
            destinations.forEach(action = { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.unselectedIcon),
                            contentDescription = destination.titleTextId
                        )
                    },
                    label = { Text(destination.titleTextId) },
                )
            })
        }
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}