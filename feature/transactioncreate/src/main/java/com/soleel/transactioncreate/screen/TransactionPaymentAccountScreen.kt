package com.soleel.transactioncreate.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soleel.transactioncreate.TransactionCreateViewModel
import com.soleel.transactioncreate.TransactionUiCreate
import com.soleel.transactioncreate.TransactionUiEvent
import kotlin.reflect.KFunction1


@Composable
internal fun TransactionPaymentAccountRoute(
    onCancelClick: () -> Unit,
    fromPaymentAccountToType: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionPaymentAccountScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromPaymentAccountToType = fromPaymentAccountToType
    )
}

@Preview
@Composable
fun TransactionPaymentAccountScreenPreview() {
    TransactionPaymentAccountScreen(
        modifier = Modifier,
        onCancelClick = {},
        transactionUiCreate = TransactionUiCreate(),
        onTransactionCreateUiEvent = {},
        fromPaymentAccountToType = {}
    )
}

@Composable
fun TransactionPaymentAccountScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromPaymentAccountToType: () -> Unit
) {
}