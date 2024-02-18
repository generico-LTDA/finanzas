package com.soleel.paymentaccountcreate.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soleel.paymentaccountcreate.CreatePaymentAccountCenterAlignedTopAppBar
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.ui.state.PaymentAccountCreateEventUi
import com.soleel.ui.state.PaymentAccountCreateUi
import com.soleel.ui.R


@Composable
internal fun PaymentAccountNameRoute(
    modifier: Modifier = Modifier,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    fromNameToAmount: () -> Unit,

    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi = viewModel.paymentAccountCreateUi

    Log.d("finanzas", "PaymentAccountNameRoute.type: ${paymentAccountCreateUi.accountType}")

    PaymentAccountNameScreen(
        modifier = modifier,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,

        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi,

        fromNameToAmount = fromNameToAmount
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PaymentAccountNameScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit,
    fromNameToAmount: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(
        topBar = { CreatePaymentAccountCenterAlignedTopAppBar(onCancelClick = onCancelClick) },
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
                        enabled = paymentAccountCreateUi.name.isNotBlank(),
                        content = { Text(text = "Avanzar a amount") }
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp),
                content = {
                    EnterTransactionNameTextField(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
                    )
                }
            )
        }
    )
}

@Composable
fun EnterTransactionNameTextField(
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {
    OutlinedTextField(
        value = paymentAccountCreateUi.name,
        onValueChange = {
            onPaymentAccountCreateEventUi(
                PaymentAccountCreateEventUi.NameChanged(it)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != paymentAccountCreateUi.accountType,
        label = { Text(text = stringResource(id = R.string.attribute_name_payment_account_title)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (paymentAccountCreateUi.nameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = paymentAccountCreateUi.nameError!!),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (paymentAccountCreateUi.nameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = paymentAccountCreateUi.nameError != null,
        singleLine = true
    )
}

