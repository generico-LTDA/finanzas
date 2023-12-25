package com.soleel.finanzas.ui

import androidx.compose.runtime.Composable
import com.soleel.finanzas.navigation.FinanzasNavHost

@Composable
fun FinanzasApp(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {
    FinanzasNavHost(appState = appState)
}