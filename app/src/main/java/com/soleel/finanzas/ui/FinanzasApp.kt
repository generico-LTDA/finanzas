package com.soleel.finanzas.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.finanzas.navigation.FinanzasNavHost
import com.soleel.finanzas.navigation.TopLevelDestination
import com.soleel.home.navigation.HomeBottomNavigationItems


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinanzasApp(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {

//    val unreadDestinations by appState.topLevelDestinationsWithUnreadResources.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                FinanzasBottomBar(
                    destinations = appState.topLevelDestinations(),
//                    destinationsWithUnreadResources = unreadDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.getCurrentDestination(),
                )
            }
        },
        content = { FinanzasNavHost(appState = appState) }
    )
}

@Composable
private fun FinanzasBottomBar(
    destinations: List<TopLevelDestination>,
//    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        content = {
            destinations.forEach { destination ->
//                val hasUnread = destinationsWithUnreadResources.contains(destination)
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
//                    modifier = if (hasUnread) Modifier.notificationDot() else Modifier,
                )
            }
        }
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false