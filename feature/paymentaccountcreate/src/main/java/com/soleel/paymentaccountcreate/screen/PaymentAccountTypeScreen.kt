package com.soleel.paymentaccountcreate.screen


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.paymentaccountcreate.CreatePaymentAccountCenterAlignedTopAppBar
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.paymentaccountcreate.util.PaymentAccountCards
import com.soleel.ui.R
import com.soleel.ui.state.PaymentAccountCreateEventUi
import com.soleel.ui.state.PaymentAccountCreateUi
import com.soleel.ui.template.PaymentAccountCard


@Composable
internal fun CreateSelectPaymentAccountTypeRoute(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit,
    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi: PaymentAccountCreateUi = viewModel.paymentAccountCreateUi

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
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                content = {
//                    Button(
//                        onClick = { fromTypeToName() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
//                        enabled = 0 != paymentAccountCreateUi.accountType,
//                        content = { Text(text = "Avanzar a Name") }
//                    )
//                }
//            )
//        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .padding(top = it.calculateTopPadding()),
                content = {
                    SelectPaymentAccountType(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi,
                        fromTypeToName = fromTypeToName
                    )
                }
            )
        }
    )
}

@Composable
fun SelectPaymentAccountType(
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit,
    fromTypeToName: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = PaymentAccountCards.cardsList,
                itemContent = { paymentAccountCard ->
                    PaymentAccountCard(
                        paymentAccountCardItem = paymentAccountCard,
                        onClick = {
                            fromTypeToName()
                            onPaymentAccountCreateEventUi(
                                PaymentAccountCreateEventUi.AccountTypeChangedUi(
                                    accountType = paymentAccountCard.type
                                )
                            )

                        }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTypeAccountDropdownMenu(
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {
    val accountTypes: List<Pair<Int, String>> = PaymentAccountTypeConstant.idToValueList

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
                        text = if (paymentAccountCreateUi.accountTypeError == null)
                            stringResource(id = R.string.required_field) else
                            stringResource(id = paymentAccountCreateUi.accountTypeError!!),
                        textAlign = TextAlign.End,
                    )
                },
                isError = paymentAccountCreateUi.accountTypeError != null,
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
                                    onPaymentAccountCreateEventUi(
                                        PaymentAccountCreateEventUi.AccountTypeChangedUi(
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