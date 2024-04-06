package com.soleel.transactioncreate

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.common.constants.TransactionCategoryConstant
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.R
import com.soleel.ui.template.TransactionCreateTopAppBar


@Composable
internal fun TransactionCreateRoute(
    modifier: Modifier = Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    fromInitToPaymentAccount: () -> Unit,
    viewModel: TransactionCreateViewModel = hiltViewModel()
) {
    val paymentAccountsUiState by viewModel.paymentAccountsUiState.collectAsStateWithLifecycle()

    TransactionCreateScreen(
        modifier = modifier,
        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,
        onBackClick = onBackClick,
        fromInitToPaymentAccount = fromInitToPaymentAccount,
        paymentAccountsUiState = paymentAccountsUiState,
        onPaymentAccountsUiEvent = viewModel::onPaymentAccountsUiEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun TransactionCreateScreen(
    modifier: Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    fromInitToPaymentAccount: () -> Unit,
    paymentAccountsUiState: PaymentAccountsUiState,
    onPaymentAccountsUiEvent: (PaymentAccountsUiEvent) -> Unit
) {

    BackHandler(
        enabled = true,
        onBack = {
            onShowBottomBar()
            onShowAddFloating()
            onBackClick()
        }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                onClick = {
                    onShowBottomBar()
                    onShowAddFloating()
                    onBackClick()
                }
            )
        },
        content = {
            when (paymentAccountsUiState) {
                is PaymentAccountsUiState.Success -> fromInitToPaymentAccount()

                is PaymentAccountsUiState.Error -> CreateTransactionErrorScreen(
                    modifier = modifier,
                    onRetry = { onPaymentAccountsUiEvent(PaymentAccountsUiEvent.Retry) }
                )

                is PaymentAccountsUiState.Loading -> TransactionCreateLoadingScreen()
            }
        }
    )
}



@Composable
fun CreateTransactionErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Error de carga",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hubo un problema al cargar los datos. Inténtalo de nuevo.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun TransactionCreateLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            CircularProgressIndicator(
                color = ProgressIndicatorDefaults.circularColor,
                strokeWidth = 5.dp,
                trackColor = ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )
        }
    )
}
