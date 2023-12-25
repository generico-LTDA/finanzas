package com.soleel.createpaymentaccount.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CreatePaymentAccountNavigationItems(
    var title: String,
    var icon: ImageVector,
    var screenRoute: String
) {
    data object AddPaymentAccount :
        CreatePaymentAccountNavigationItems("Añadir Cuenta de Pago", Icons.Filled.ArrowBack, "addPaymentAccount")
}
