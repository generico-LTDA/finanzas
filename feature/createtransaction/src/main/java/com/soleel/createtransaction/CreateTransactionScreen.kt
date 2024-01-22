package com.soleel.createtransaction

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.R
import com.soleel.validation.validator.AmountValidator


@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    viewModel: CreateTransactionViewModel = hiltViewModel()
) {
    val createTransactionUiCreate = viewModel.createTransactionUiCreate
    val paymentAccountsUiState by viewModel.paymentAccountsUiState.collectAsStateWithLifecycle()

    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    CreateTransactionScreen(
        createTransactionUiCreate = createTransactionUiCreate,
        paymentAccountsUiState = paymentAccountsUiState,

        modifier = modifier,

        onBackClick = onBackClick,
        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,

        onCreateTransactionUiEvent = viewModel::onCreateTransactionUiEvent,
        onPaymentAccountsUiEvent = viewModel::onPaymentAccountsUiEvent
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CreateTransactionScreen(
    modifier: Modifier,
    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccountsUiState: PaymentAccountsUiState,

    onBackClick: () -> Unit,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,
    onPaymentAccountsUiEvent: (PaymentAccountsUiEvent) -> Unit
) {

    if (createTransactionUiCreate.isTransactionSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = { CreateTransactionCenterAlignedTopAppBar(onBackClick = onBackClick) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onCreateTransactionUiEvent(CreateTransactionUiEvent.Submit) },
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
            when (paymentAccountsUiState) {
                is PaymentAccountsUiState.Success -> CreateTransactionSuccessScreen(
                    modifier = modifier,
                    createTransactionUiCreate = createTransactionUiCreate,
                    paymentAccounts = paymentAccountsUiState.paymentAccounts,
                    onCreateTransactionUiEvent = onCreateTransactionUiEvent,
                    paddingValues = it
                )

                is PaymentAccountsUiState.Error -> CreateTransactionErrorScreen(
                    modifier = modifier,
                    onRetry = { onPaymentAccountsUiEvent(PaymentAccountsUiEvent.Retry) }
                )

                is PaymentAccountsUiState.Loading -> CreateTransactionLoadingScreen()
            }
        }
    )

}

@Composable
private fun CreateTransactionSuccessScreen(
    modifier: Modifier,
    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccounts: List<PaymentAccount>,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        content = {
            CreateTransactionForm(
                createTransactionUiCreate = createTransactionUiCreate,
                paymentAccounts = paymentAccounts,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
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
    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccounts: List<PaymentAccount>,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    Column(
        modifier = Modifier.padding(16.dp),
        content = {

            SelectPaymentAccountDropdownMenu(
                createTransactionUiCreate = createTransactionUiCreate,
                paymentAccounts = paymentAccounts,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = createTransactionUiCreate.name,
                onValueChange = {
                    onCreateTransactionUiEvent(
                        CreateTransactionUiEvent.NameChanged(
                            it
                        )
                    )
                },
                label = { Text(text = stringResource(id = R.string.attribute_name_trasaction_title)) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createTransactionUiCreate.nameError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createTransactionUiCreate.nameError),
                        textAlign = TextAlign.End,
                    )
                },
                trailingIcon = {
                    if (createTransactionUiCreate.nameError != null) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            tint = Color.Red, // Cambiar color
                            contentDescription = "Nombre de la transaccion a crear"
                        )
                    }
                },
                isError = createTransactionUiCreate.nameError != null,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = createTransactionUiCreate.amount,
                onValueChange = { input ->

                    val trimmed = input
                        .trimStart('0')
                        .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })

                    if (trimmed.isNotEmpty() && trimmed.length <= AmountValidator.maxCharLimit) {
                        onCreateTransactionUiEvent(CreateTransactionUiEvent.AmountChanged(trimmed))
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.attribute_amount_trasaction_title)) },
                trailingIcon = {
                    if (createTransactionUiCreate.amountError != null) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            tint = Color.Red, // Cambiar color
                            contentDescription = "Monto de la transaccion a crear"
                        )
                    }
                },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createTransactionUiCreate.amountError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createTransactionUiCreate.amountError),
                        textAlign = TextAlign.End,
                    )
                },
                isError = createTransactionUiCreate.amountError != null,
                visualTransformation = currencyVisualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPaymentAccountDropdownMenu(
    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccounts: List<PaymentAccount>,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {

    var selectedOption by remember(calculation = { mutableStateOf("") })
    var expanded by remember(calculation = { mutableStateOf(false) })

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = false == expanded },
        content = {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                label = { Text("Cuenta de pago") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createTransactionUiCreate.paymentAccountError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createTransactionUiCreate.paymentAccountError),
                        textAlign = TextAlign.End,
                    )
                },
                isError = createTransactionUiCreate.paymentAccountError != null,
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    paymentAccounts.forEach(
                        action = { paymentAccount ->

                            val transformedAmount = currencyVisualTransformation.filter(
                                AnnotatedString(text = paymentAccount.amount.toString()))

                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Text(text = paymentAccount.name)
                                            Text(text = "${transformedAmount.text}")
                                        }
                                    )
                                },
                                onClick = {

                                    selectedOption =
                                        "${paymentAccount.name} - ${transformedAmount.text}"
                                    expanded = false
                                    onCreateTransactionUiEvent(
                                        CreateTransactionUiEvent.PaymentAccountChanged(
                                            paymentAccount = paymentAccount
                                        )
                                    )
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    )
                }
            )
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