package com.soleel.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.ui.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddModalBottomSheet(
    onCreatePaymentAccount: () -> Unit,
    onCreateTransaction: () -> Unit,
    onHideBottomBar: () -> Unit,
    onHideAddFloating: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    viewModel: AddViewModel = hiltViewModel(),
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        content = {
            AddCards(
                onCreatePaymentAccount = onCreatePaymentAccount,
                onCreateTransaction = onCreateTransaction,
                onHideBottomBar = onHideBottomBar,
                onHideAddFloating = onHideAddFloating,
                onDismiss = onDismiss,
                viewModel = viewModel
            )
        }
    )
}

@Composable
private fun AddCards(
    onCreatePaymentAccount: () -> Unit,
    onCreateTransaction: () -> Unit,
    onHideBottomBar: () -> Unit,
    onHideAddFloating: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: AddViewModel,
) {
    val paymentAccountsUiState: AddUiState by viewModel.addUiState.collectAsState()

    Row(
        modifier = Modifier.navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AddCardItem(
            modifier = Modifier.weight(1f),
            iconResId = R.drawable.ic_add_account,
            name = "Crear cuenta",
            onClick = onCreatePaymentAccount,
            onHideBottomBar = onHideBottomBar,
            onHideAddFloating = onHideAddFloating,
            onDismiss = onDismiss
        )
        AddCardItem(
            modifier = Modifier.weight(1f),
            iconResId = R.drawable.ic_add_transaction,
            name = "Crear transacción",
            onClick = onCreateTransaction,
            onHideBottomBar = onHideBottomBar,
            onHideAddFloating = onHideAddFloating,
            onDismiss = onDismiss,
            isPaymentAccountEmpty = paymentAccountsUiState.isPaymentAccountEmpty,
        )
    }
}

@Composable
private fun AddCardItem(
    modifier: Modifier,
    iconResId: Int,
    name: String,
    onClick: () -> Unit,
    onHideBottomBar: () -> Unit,
    onHideAddFloating: () -> Unit,
    onDismiss: () -> Unit,
    isPaymentAccountEmpty: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                enabled = !isPaymentAccountEmpty,
                onClick = {
                    onClick()
                    onHideBottomBar()
                    onHideAddFloating()
                    onDismiss()
                }),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Icono de agregar",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = name,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    )
}