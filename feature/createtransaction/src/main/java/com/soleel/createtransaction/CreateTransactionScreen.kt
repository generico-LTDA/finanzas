package com.soleel.createtransaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.ui.R

@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
//    onCancelClick: () -> Unit,
    viewModel: CreateTransactionViewModel = hiltViewModel(),
) {
    CreateTransactionScreen(
        modifier = modifier,
        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,
        onBackClick = onBackClick,
//        onCancelClick = onCancelClick,
        viewModel = viewModel
    )
}

@Composable
private fun CreateTransactionScreen(
    modifier: Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
//    onCancelClick: () -> Unit,
    viewModel: CreateTransactionViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

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
                onClick = { viewModel.onEvent(CreateTransactionUiEvent.Submit) },
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

//            addPaymentAccountUiState.userMessage?.let { userMessage ->
//                LaunchedEffect(
//                    key1 = userMessage,
//                    block = {
//                        snackbarHostState.showSnackbar(
//                            message = userMessage,
//                            duration = SnackbarDuration.Short
//                        )
//                        viewModel.userMessageShown()
//                    }
//                )
//            }
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
                        contentDescription = "Back to Home",
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
                onValueChange = { viewModel.onEvent(CreateTransactionUiEvent.NameChanged(it)) },
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
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_name),
//                        contentDescription = "Nombre de la transaccion a crear"
//                    )
//                },

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
//
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
