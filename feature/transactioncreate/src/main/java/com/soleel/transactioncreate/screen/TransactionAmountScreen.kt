package com.soleel.transactioncreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soleel.transactioncreate.TransactionCreateViewModel
import com.soleel.transactioncreate.TransactionUiCreate
import com.soleel.transactioncreate.TransactionUiEvent
import com.soleel.ui.R
import com.soleel.ui.template.TransactionCreateTopAppBar


@Composable
internal fun TransactionAmountRoute(
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionAmountScreen(
        modifier = Modifier,
        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        onSaveClick = onSaveClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent
    )
}

@Preview
@Composable
fun TransactionAmountScreenPreview() {
    TransactionAmountScreen(
        modifier = Modifier,
        onShowBottomBar = {},
        onShowAddFloating = {},
        onCancelClick = {},
        onBackClick = {},
        onSaveClick = {},
        transactionUiCreate = TransactionUiCreate(),
        onTransactionCreateUiEvent = {},
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionAmountScreen(
    modifier: Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    if (transactionUiCreate.isTransactionSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onSaveClick()
    }

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_amount_top_app_bar_subtitle,
                onClick = onCancelClick
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = {
                            onShowBottomBar()
                            onShowAddFloating()
                            onSaveClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
//                        enabled = 0 != paymentAccountUiCreate.type,
                        content = { Text(text = "Guardar transaccion") }
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "Input Amount Screen",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    )

}
