package com.soleel.paymentaccounttype


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.paymentaccountcreate.CreatePaymentAccountCenterAlignedTopAppBar
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.ui.PaymentAccountCreateEventUi
import com.soleel.ui.PaymentAccountCreateUi
import com.soleel.ui.R


@Composable
internal fun CreateSelectPaymentAccountTypeRoute(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit,
    viewModel: PaymentAccountCreateViewModel = hiltViewModel()
) {
    val paymentAccountCreateUi = viewModel.paymentAccountCreateUi

    CreateSelectPaymentAccountTypeScreen(
        modifier = modifier,

        onCancelClick = onCancelClick,

        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi,

        fromTypeToName = fromTypeToName
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun CreateSelectPaymentAccountTypeScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit,
    fromTypeToName: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    Scaffold(
        topBar = { CreatePaymentAccountCenterAlignedTopAppBar(onCancelClick = onCancelClick) },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = { fromTypeToName() },
                        modifier = Modifier.fillMaxWidth(0.9f).height(64.dp),
//                        enabled = 0 != paymentAccountCreateUi.accountType,
                        content = { Text(text = "Avanzar a Name") }
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
                    text = "Type Screen",
                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    )
}
