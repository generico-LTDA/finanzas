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
internal fun TransactionNameRoute(
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionNameScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromNameToAmount = fromNameToAmount
    )
}

@Preview
@Composable
fun TransactionNameScreenPreview() {
    TransactionNameScreen(
        modifier = Modifier,
        onCancelClick = {},
        onBackClick = {},
        transactionUiCreate = TransactionUiCreate(),
        onTransactionCreateUiEvent = {},
        fromNameToAmount = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionNameScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromNameToAmount: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_name_top_app_bar_subtitle,
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
                        onClick = { fromNameToAmount() },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
//                        enabled = 0 != paymentAccountUiCreate.type,
                        content = { Text(text = "Avanzar a ingresar monto") }
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
                    text = "Input Name Screen",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    )

}