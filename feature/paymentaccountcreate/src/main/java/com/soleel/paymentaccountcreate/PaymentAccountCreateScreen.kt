package com.soleel.paymentaccountcreate

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.common.constants.AccountTypeConstant
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.R
import com.soleel.validation.validator.TransactionAmountValidator


@Composable
internal fun CreatePaymentAccountRoute(
    modifier: Modifier = Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    viewModel: CreatePaymentAccountViewModel = hiltViewModel(),
) {
    val createPaymentAccountUiCreate = viewModel.createPaymentAccountUiCreate

    CreatePaymentAccountScreen(
        modifier = modifier,

        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,

        createPaymentAccountUiCreate = createPaymentAccountUiCreate,

        onCreatePaymentAccountUiEvent = viewModel::onCreatePaymentAccountUiEvent
    )
}

@Composable
internal fun CreatePaymentAccountScreen(
    modifier: Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    createPaymentAccountUiCreate: CreatePaymentAccountUiCreate,

    onCreatePaymentAccountUiEvent: (CreatePaymentAccountUiEvent) -> Unit
) {

    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    if (createPaymentAccountUiCreate.isPaymentAccountSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = { CreatePaymentAccountCenterAlignedTopAppBar(onCancelClick = onCancelClick) },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = { onCreatePaymentAccountUiEvent(CreatePaymentAccountUiEvent.Submit) },
                        modifier = Modifier.fillMaxWidth(0.9f).height(64.dp),
                        enabled = 0 != createPaymentAccountUiCreate.accountType
                                && createPaymentAccountUiCreate.name.isNotBlank()
                                && createPaymentAccountUiCreate.amount.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.add_payment_account_title)) }
                    )
                }
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                content = {
                    CreatePaymentAccountForm(
                        createPaymentAccountUiCreate = createPaymentAccountUiCreate,
                        onCreatePaymentAccountUiEvent = onCreatePaymentAccountUiEvent
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePaymentAccountCenterAlignedTopAppBar(
    onCancelClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.create_payment_account_title),
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

//@Preview
//@Composable
//fun PaymentAccountCard(
//    viewModel: CreatePaymentAccountViewModel
//) {
//    val paymentAccount by viewModel.createPaymentAccountUiCreate
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//        border = BorderStroke(width = 6.dp, color = Color.Black),
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                content = {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically,
//                        content = {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                content = {
//                                    Icon(
//                                        imageVector = Icons.Filled.AccountBox,
//                                        contentDescription = "Payment Account Icon",
//                                        tint = Color.Black
//                                    )
//                                    Text(
//                                        text = paymentAccount.accountType.toString(),
//                                        style = MaterialTheme.typography.titleMedium
//                                    )
//                                }
//                            )
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                content = {
//                                    Text(
//                                        text = "$3.000.000",
//                                        style = MaterialTheme.typography.titleLarge
//                                    )
//                                    Text(
//                                        text = "$1.500.000 ocupado",
//                                        style = MaterialTheme.typography.titleSmall,
//                                    )
//                                }
//                            )
//                        }
//                    )
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 96.dp),
//                        content = {
//                            Text(
//                                text = "1234 5678 9012 3456",
//                                style = MaterialTheme.typography.displaySmall,
//                                textAlign = TextAlign.Center
//                            )
//                            Text(
//                                text = "Nombre de la cuenta",
//                                style = MaterialTheme.typography.titleLarge,
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    )
//                }
//            )
//        }
//    )
//}

@Composable
fun CreatePaymentAccountForm(
    createPaymentAccountUiCreate: CreatePaymentAccountUiCreate,
    onCreatePaymentAccountUiEvent: (CreatePaymentAccountUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        content = {
            SelectTypeAccountDropdownMenu(
                createPaymentAccountUiCreate = createPaymentAccountUiCreate,
                onCreatePaymentAccountUiEvent = onCreatePaymentAccountUiEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            EnterTransactionNameTextField(
                createPaymentAccountUiCreate = createPaymentAccountUiCreate,
                onCreatePaymentAccountUiEvent = onCreatePaymentAccountUiEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            EnterTransactionAmountTextFlied(
                createPaymentAccountUiCreate = createPaymentAccountUiCreate,
                onCreatePaymentAccountUiEvent = onCreatePaymentAccountUiEvent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTypeAccountDropdownMenu(
    createPaymentAccountUiCreate: CreatePaymentAccountUiCreate,
    onCreatePaymentAccountUiEvent: (CreatePaymentAccountUiEvent) -> Unit
) {
    val accountTypes: List<Pair<Int, String>> = AccountTypeConstant.idToValueList

    var selectedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

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
                label = { Text(text = stringResource(id = R.string.attribute_type_payment_account_title)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (createPaymentAccountUiCreate.accountTypeError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = createPaymentAccountUiCreate.accountTypeError),
                        textAlign = TextAlign.End,
                    )
                },
                isError = createPaymentAccountUiCreate.accountTypeError != null,
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    accountTypes.forEach(
                        action = { accountType ->
                            DropdownMenuItem(
                                text = { Text(text = accountType.second) },
                                onClick = {

                                    selectedOption = accountType.second
                                    expanded = false
                                    onCreatePaymentAccountUiEvent(
                                        CreatePaymentAccountUiEvent.AccountTypeChanged(
                                            accountType = accountType.first
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
    createPaymentAccountUiCreate: CreatePaymentAccountUiCreate,
    onCreatePaymentAccountUiEvent: (CreatePaymentAccountUiEvent) -> Unit
) {
    OutlinedTextField(
        value = createPaymentAccountUiCreate.name,
        onValueChange = {
            onCreatePaymentAccountUiEvent(
                CreatePaymentAccountUiEvent.NameChanged(it)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != createPaymentAccountUiCreate.accountType,
        label = { Text(text = stringResource(id = R.string.attribute_name_payment_account_title)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (createPaymentAccountUiCreate.nameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = createPaymentAccountUiCreate.nameError),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (createPaymentAccountUiCreate.nameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = createPaymentAccountUiCreate.nameError != null,
        singleLine = true
    )
}

@Composable
fun EnterTransactionAmountTextFlied(
    createPaymentAccountUiCreate: CreatePaymentAccountUiCreate,
    onCreatePaymentAccountUiEvent: (CreatePaymentAccountUiEvent) -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    OutlinedTextField(
        value = createPaymentAccountUiCreate.amount,
        onValueChange = {
            val trimmed = it
                .trimStart('0')
                .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })

            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
                onCreatePaymentAccountUiEvent(CreatePaymentAccountUiEvent.AmountChanged(trimmed))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != createPaymentAccountUiCreate.accountType,
        label = { Text(text = stringResource(id = R.string.attribute_amount_payment_account_title)) },
        trailingIcon = {
            if (createPaymentAccountUiCreate.amountError != null) {
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
                text = if (createPaymentAccountUiCreate.amountError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = createPaymentAccountUiCreate.amountError),
                textAlign = TextAlign.End,
            )
        },
        isError = createPaymentAccountUiCreate.amountError != null,
        visualTransformation = currencyVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}