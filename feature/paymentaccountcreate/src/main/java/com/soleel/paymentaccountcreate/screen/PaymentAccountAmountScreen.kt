package com.soleel.paymentaccountcreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soleel.paymentaccountcreate.CreatePaymentAccountCenterAlignedTopAppBar
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.state.PaymentAccountCreateEventUi
import com.soleel.ui.state.PaymentAccountCreateUi
import com.soleel.ui.R
import com.soleel.validation.validator.TransactionAmountValidator


@Composable
internal fun PaymentAccountAmountRoute(
    modifier: Modifier = Modifier,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi = viewModel.paymentAccountCreateUi

    PaymentAccountAmountScreen(
        modifier = modifier,

        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,

        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PaymentAccountAmountScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    if (paymentAccountCreateUi.isPaymentAccountSaved) {
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
                        onClick = {  },
                        modifier = Modifier.fillMaxWidth(0.9f).height(64.dp),
                        enabled = 0 != paymentAccountCreateUi.accountType
                                && paymentAccountCreateUi.name.isNotBlank()
                                && paymentAccountCreateUi.amount.isNotBlank(),
                        content = { Text(text = "guardar payment") }
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
                    EnterTransactionAmountTextFlied(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
                    )
                }
            )
        }
    )
}

@Composable
fun EnterTransactionAmountTextFlied(
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    OutlinedTextField(
        value = paymentAccountCreateUi.amount,
        onValueChange = {
            val trimmed = it
                .trimStart('0')
                .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })

            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
                onPaymentAccountCreateEventUi(PaymentAccountCreateEventUi.AmountChanged(trimmed))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = 0 != paymentAccountCreateUi.accountType,
        label = { Text(text = stringResource(id = R.string.attribute_amount_payment_account_title)) },
        trailingIcon = {
            if (paymentAccountCreateUi.amountError != null) {
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
                text = if (paymentAccountCreateUi.amountError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = paymentAccountCreateUi.amountError!!),
                textAlign = TextAlign.End,
            )
        },
        isError = paymentAccountCreateUi.amountError != null,
        visualTransformation = currencyVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}