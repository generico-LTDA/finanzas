package com.soleel.createtransaction

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
import com.soleel.common.constants.CategoryTypeConstant
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.R
import com.soleel.validation.validator.TransactionAmountValidator


@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    viewModel: CreateTransactionViewModel = hiltViewModel()
) {
    val createTransactionUiCreate = viewModel.createTransactionUiCreate
    val paymentAccountsUiState by viewModel.paymentAccountsUiState.collectAsStateWithLifecycle()

    CreateTransactionScreen(
        modifier = modifier,

        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,

        createTransactionUiCreate = createTransactionUiCreate,
        paymentAccountsUiState = paymentAccountsUiState,

        onCreateTransactionUiEvent = viewModel::onCreateTransactionUiEvent,
        onPaymentAccountsUiEvent = viewModel::onPaymentAccountsUiEvent
    )
}

@Composable
private fun CreateTransactionScreen(
    modifier: Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccountsUiState: PaymentAccountsUiState,

    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,
    onPaymentAccountsUiEvent: (PaymentAccountsUiEvent) -> Unit
) {

    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    if (createTransactionUiCreate.isTransactionSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = { CreateTransactionCenterAlignedTopAppBar(onCancelClick = onCancelClick) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onCreateTransactionUiEvent(CreateTransactionUiEvent.Submit) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Save transaction"
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
    onCancelClick: () -> Unit
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
                onClick = { onCancelClick() },
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

    var selectedPaymentAccountOption by remember(calculation = { mutableStateOf("") })
    var expandedPaymentAccount by remember(calculation = { mutableStateOf(false) })

    var selectedTransactionTypeOption by remember(calculation = { mutableStateOf("") })
    var expandedTransactionType by remember(calculation = { mutableStateOf(false) })

    var selectedCategoryTypeOption by remember(calculation = { mutableStateOf("") })
    var expandedCategoryType by remember(calculation = { mutableStateOf(false) })

    Column(
        modifier = Modifier.padding(16.dp),
        content = {

            SelectPaymentAccountDropdownMenu(
                createTransactionUiCreate = createTransactionUiCreate,
                paymentAccounts = paymentAccounts,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent,

                selectedPaymentAccountOption = selectedPaymentAccountOption,
                changeSelectedPaymentAccountOption = { value: String ->
                    selectedPaymentAccountOption = value
                },

                expandedPaymentAccount = expandedPaymentAccount,
                changeExpandedPaymentAccount = { value: Boolean -> expandedPaymentAccount = value },

                resetOtherFields = {
                    selectedTransactionTypeOption = ""
                    selectedCategoryTypeOption = ""
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectTransactionTypeDropdownMenu(
                createTransactionUiCreate = createTransactionUiCreate,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent,

                selectedTransactionTypeOption = selectedTransactionTypeOption,
                changeSelectedTransactionTypeOption = { value: String ->
                    selectedTransactionTypeOption = value
                },

                expandedTransactionType = expandedTransactionType,
                changeExpandedPaymentAccount = { value: Boolean ->
                    expandedTransactionType = value
                },

                resetOtherFields = {
                    selectedCategoryTypeOption = ""
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectCategoryTypeDropdownMenu(
                createTransactionUiCreate = createTransactionUiCreate,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent,

                selectedCategoryTypeOption = selectedCategoryTypeOption,
                changeSelectedCategoryTypeOption = { value: String ->
                    selectedCategoryTypeOption = value
                },

                expandedCategoryType = expandedCategoryType,
                changeExpandedCategoryType = { value: Boolean ->
                    expandedCategoryType = value
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            EnterTransactionNameTextField(
                createTransactionUiCreate = createTransactionUiCreate,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            EnterTransactionAmountTextFlied(
                createTransactionUiCreate = createTransactionUiCreate,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPaymentAccountDropdownMenu(
    createTransactionUiCreate: CreateTransactionUiCreate,
    paymentAccounts: List<PaymentAccount>,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,

    selectedPaymentAccountOption: String,
    changeSelectedPaymentAccountOption: (String) -> Unit,

    expandedPaymentAccount: Boolean,
    changeExpandedPaymentAccount: (Boolean) -> Unit,

    resetOtherFields: () -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expandedPaymentAccount,
        onExpandedChange = { changeExpandedPaymentAccount(false == expandedPaymentAccount) },
        content = {
            OutlinedTextField(
                value = selectedPaymentAccountOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                label = { Text("Cuenta de pago") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaymentAccount) },
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
                expanded = expandedPaymentAccount,
                onDismissRequest = { changeExpandedPaymentAccount(false) },
                content = {
                    paymentAccounts.forEach(
                        action = { paymentAccount ->

                            val transformedAmount = currencyVisualTransformation.filter(
                                AnnotatedString(text = paymentAccount.amount.toString())
                            )

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
                                    changeSelectedPaymentAccountOption("${paymentAccount.name} - ${transformedAmount.text}")
                                    changeExpandedPaymentAccount(false)

                                    onCreateTransactionUiEvent(
                                        CreateTransactionUiEvent.PaymentAccountChanged(
                                            paymentAccount = paymentAccount
                                        )
                                    )

                                    resetOtherFields()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTransactionTypeDropdownMenu(
    createTransactionUiCreate: CreateTransactionUiCreate,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,

    selectedTransactionTypeOption: String,
    changeSelectedTransactionTypeOption: (String) -> Unit,

    expandedTransactionType: Boolean,
    changeExpandedPaymentAccount: (Boolean) -> Unit,

    resetOtherFields: () -> Unit
) {
    val transactionTypes: List<Pair<Int, String>> = TransactionTypeConstant.idToValueList

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expandedTransactionType,
        onExpandedChange = { changeExpandedPaymentAccount(false == expandedTransactionType) },
        content = {
            OutlinedTextField(
                value = selectedTransactionTypeOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = createTransactionUiCreate.paymentAccount.id.isNotBlank(),
                readOnly = true,
                label = { Text("Tipo de transaccion") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTransactionType) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createTransactionUiCreate.transactionTypeError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createTransactionUiCreate.transactionTypeError),
                        textAlign = TextAlign.End,
                    )
                },
                isError = createTransactionUiCreate.transactionTypeError != null,
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expandedTransactionType,
                onDismissRequest = { changeExpandedPaymentAccount(false) },
                content = {
                    transactionTypes.forEach(
                        action = { transactionType ->
                            DropdownMenuItem(
                                text = { Text(text = transactionType.second) },
                                onClick = {
                                    changeSelectedTransactionTypeOption(transactionType.second)
                                    changeExpandedPaymentAccount(false)
                                    onCreateTransactionUiEvent(
                                        CreateTransactionUiEvent.TransactionTypeChanged(
                                            transactionType = transactionType.first
                                        )
                                    )
                                    resetOtherFields()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategoryTypeDropdownMenu(
    createTransactionUiCreate: CreateTransactionUiCreate,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,

    selectedCategoryTypeOption: String,
    changeSelectedCategoryTypeOption: (String) -> Unit,

    expandedCategoryType: Boolean,
    changeExpandedCategoryType: (Boolean) -> Unit
) {
    val categoryTypes: List<Pair<Int, String>> = CategoryTypeConstant.idToValueList(
        transactionType = createTransactionUiCreate.transactionType,
        accountType = createTransactionUiCreate.paymentAccount.accountType
    )

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expandedCategoryType,
        onExpandedChange = { changeExpandedCategoryType(false == expandedCategoryType) },
        content = {
            OutlinedTextField(
                value = selectedCategoryTypeOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = 0 != createTransactionUiCreate.transactionType,
                readOnly = true,
                label = { Text("Categoria de transaccion") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoryType) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createTransactionUiCreate.categoryTypeError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createTransactionUiCreate.categoryTypeError),
                        textAlign = TextAlign.End,
                    )
                },
                isError = createTransactionUiCreate.categoryTypeError != null,
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expandedCategoryType,
                onDismissRequest = { changeExpandedCategoryType(false) },
                content = {
                    categoryTypes.forEach(
                        action = { categoryType ->
                            DropdownMenuItem(
                                text = { Text(text = categoryType.second) },
                                onClick = {
                                    changeSelectedCategoryTypeOption(categoryType.second)
                                    changeExpandedCategoryType(false)
                                    onCreateTransactionUiEvent(
                                        CreateTransactionUiEvent.CategoryTypeChanged(
                                            categoryType = categoryType.first
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
fun EnterTransactionNameTextField(
    createTransactionUiCreate: CreateTransactionUiCreate,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {
    OutlinedTextField(
        value = createTransactionUiCreate.name,
        onValueChange = {
            onCreateTransactionUiEvent(
                CreateTransactionUiEvent.NameChanged(
                    it
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != createTransactionUiCreate.categoryType,
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
}

@Composable
fun EnterTransactionAmountTextFlied(
    createTransactionUiCreate: CreateTransactionUiCreate,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    OutlinedTextField(
        value = createTransactionUiCreate.amount,
        onValueChange = { input ->
            val trimmed = input
                .trimStart('0')
                .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })

            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
                onCreateTransactionUiEvent(CreateTransactionUiEvent.AmountChanged(trimmed))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != createTransactionUiCreate.categoryType,
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