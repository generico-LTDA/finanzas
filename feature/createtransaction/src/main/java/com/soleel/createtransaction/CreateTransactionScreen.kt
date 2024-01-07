package com.soleel.createtransaction

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.ui.R


@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateTransactionViewModel = hiltViewModel(),
) {
    val paymentAccountsUiState by viewModel.paymentAccountsUiState.collectAsState()

    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    when (paymentAccountsUiState) {
        is PaymentAccountsUiState.Success -> {

            Log.d("finanzas", "Count: " + viewModel.count)

            if (viewModel.count == 0) {
                CreateTransactionErrorScreen(
                    modifier = modifier,
                    onRetry = { viewModel.onPaymentAccountsUiEvent(PaymentAccountsUiEvent.Retry) }
                )
                viewModel.count++
            } else {
                CreateTransactionSuccessScreen(
                    modifier = modifier,
                    onShowBottomBar = onShowBottomBar,
                    onShowAddFloating = onShowAddFloating,
                    onBackClick = onBackClick,
                    viewModel = viewModel
                )
            }
        }

        is PaymentAccountsUiState.Error -> CreateTransactionErrorScreen(
            modifier = modifier,
            onRetry = { viewModel.onPaymentAccountsUiEvent(PaymentAccountsUiEvent.Retry) }
        )

        is PaymentAccountsUiState.Loading -> CreateTransactionLoadingScreen()
    }
}

@Composable
private fun CreateTransactionSuccessScreen(
    modifier: Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateTransactionViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    if (viewModel.createTransactionUiCreate.isTransactionSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = { CreateTransactionCenterAlignedTopAppBar(onBackClick = onBackClick) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onCreateTransactionUiEvent(CreateTransactionUiEvent.Submit) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Save payment account"
                    )
                },
                text = { Text(text = stringResource(id = R.string.add_trasaction_title)) },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                content = {
                    //PaymentAccountCard(viewModel)
                    CreateTransactionForm(viewModel = viewModel)
                }
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTransactionCenterAlignedTopAppBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.create_trasaction_title),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver a la pantalla anterior",
                    )
                }
            )
        }
    )
}

@Composable
fun CreateTransactionForm(
    viewModel: CreateTransactionViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp),
        content = {
//            SelectTypeAccountDropdownMenu(viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.createTransactionUiCreate.name,
                onValueChange = {
                    viewModel.onCreateTransactionUiEvent(
                        CreateTransactionUiEvent.NameChanged(
                            it
                        )
                    )
                },
                label = { Text(text = stringResource(id = R.string.attribute_name_trasaction_title)) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (viewModel.createTransactionUiCreate.nameError == null)
                            stringResource(id = R.string.required_field) else
                            viewModel.createTransactionUiCreate.nameError!!.asString(LocalContext.current),
                        textAlign = TextAlign.End,
                    )
                },
                trailingIcon = {
                    if (viewModel.createTransactionUiCreate.nameError != null) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            tint = Color.Red, // Cambiar color
                            contentDescription = "Nombre de la transaccion a crear"
                        )
                    }
                },
                isError = viewModel.createTransactionUiCreate.nameError != null,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

//            TextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = addPaymentAccountUiState.initialAmount?.toString() ?: "",
//                onValueChange = viewModel::updateInitialAmount,
//                label = { Text(text = "Monto inicial de la cuenta") },
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_money),
//                        contentDescription = "asdasd"
//                    )
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                singleLine = true
//            )
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

@Preview
@Composable
fun CreateTransactionLoadingScreen() {
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