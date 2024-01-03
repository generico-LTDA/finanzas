package com.soleel.createpaymentaccount

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.common.constants.AccountTypeConstant
import com.soleel.createpaymentaccount.navigation.CreatePaymentAccountNavigationItems
import com.soleel.ui.R


@Composable
internal fun CreatePaymentAccountRoute(
    modifier: Modifier = Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: CreatePaymentAccountViewModel = hiltViewModel(),
) {
    CreatePaymentAccountScreen(
        modifier = modifier,
        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,
        onBackClick = onBackClick,
        onCancelClick = onCancelClick,
        viewModel = viewModel
    )
}

@Composable
internal fun CreatePaymentAccountScreen(
    modifier: Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: CreatePaymentAccountViewModel,
) {

    val addPaymentAccountUiState: CreatePaymentAccountUiState by viewModel.createPaymentAccountUiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    if (addPaymentAccountUiState.isPaymentAccountSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = { CreatePaymentAccountCenterAlignedTopAppBar(onCancelClick) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = viewModel::savePaymentAccount,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Save payment account"
                    )
                },
                text = { Text(text = "Agregar cuenta") },
            )
        },
        content = { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                content = {
                    //PaymentAccountCard(viewModel)
                    CreatePaymentAccountForm(viewModel = viewModel)
                }
            )

            addPaymentAccountUiState.userMessage?.let { userMessage ->
                LaunchedEffect(
                    key1 = userMessage,
                    block = {
                        snackbarHostState.showSnackbar(
                            message = userMessage,
                            duration = SnackbarDuration.Short
                        )
                        viewModel.userMessageShown()
                    }
                )
            }
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
                text = CreatePaymentAccountNavigationItems.AddPaymentAccount.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onCancelClick() },
                content = {
                    Icon(
                        imageVector = CreatePaymentAccountNavigationItems.AddPaymentAccount.icon,
                        contentDescription = "Back to Home",
                    )
                }
            )
        }
    )
}

//@Preview
@Composable
fun PaymentAccountCard(
    viewModel: CreatePaymentAccountViewModel
) {
    val paymentAccount by viewModel.createPaymentAccountUiState.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(width = 6.dp, color = Color.Black),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        imageVector = Icons.Filled.AccountBox,
                                        contentDescription = "Payment Account Icon",
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = paymentAccount.accountType.toString(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Text(
                                        text = "$3.000.000",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "$1.500.000 ocupado",
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 96.dp),
                        content = {
                            Text(
                                text = "1234 5678 9012 3456",
                                style = MaterialTheme.typography.displaySmall,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Nombre de la cuenta",
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun CreatePaymentAccountForm(
    viewModel: CreatePaymentAccountViewModel
) {
    val addPaymentAccountUiState by viewModel.createPaymentAccountUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp),
        content = {
            SelectTypeAccountDropdownMenu(viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = addPaymentAccountUiState.name,
                onValueChange = viewModel::updateName,
                label = { Text(text = "Nombre de la cuenta") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_name),
                        contentDescription = "Nombre de la cuenta de pago a crear"
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = addPaymentAccountUiState.initialAmount?.toString() ?: "",
                onValueChange = viewModel::updateInitialAmount,
                label = { Text(text = "Monto inicial de la cuenta") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_money),
                        contentDescription = "asdasd"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTypeAccountDropdownMenu(
    viewModel: CreatePaymentAccountViewModel
) {
    val typesAccounts: List<Pair<Int, String>> = AccountTypeConstant.accountTypes

    var selectedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        content = {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type),
                        contentDescription = "Localized description"
                    )
                },
                value = selectedOption,
                onValueChange = {},
                label = { Text("Tipo de cuenta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    typesAccounts.forEach(
                        action = { option ->
                            DropdownMenuItem(
                                text = { Text(text = option.second) },
                                onClick = {
                                    selectedOption = option.second
                                    expanded = false
                                    viewModel.updateTypeAccount(option.first)
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
