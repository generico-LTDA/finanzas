package com.soleel.ui.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.ui.util.PaymentAccountCardItem
import com.soleel.ui.util.TransactionCardItem
import com.soleel.ui.util.getPaymentAccountCard
import com.soleel.ui.util.getTransactionCard
import java.util.Locale

@Preview
@Composable
fun TransactionCardPreview() {
    TransactionCard(
        paymentAccountCardItem = getPaymentAccountCard(
            typePaymentAccount = PaymentAccountTypeConstant.CREDIT
        ),
        transactionCardItem = getTransactionCard(
            transactionType = TransactionTypeConstant.INCOME,
            nameTransactionType = TransactionTypeConstant.INCOME_VALUE
        )
    )
}

@Composable
fun TransactionCard(
    paymentAccountCardItem: PaymentAccountCardItem,
    transactionCardItem: TransactionCardItem,
    onClick: () -> Unit = {},
    onClickEnable: Boolean = true
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(enabled = onClickEnable, onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = transactionCardItem.typeBackgroundColor)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                painter = painterResource(id = transactionCardItem.typeIcon),
                                contentDescription = "Transaction type",
                                modifier = Modifier.size(16.dp),
                                tint = transactionCardItem.letterColor
                            )
                            Text(
                                text = transactionCardItem.typeName.uppercase(Locale.getDefault()),
                                modifier = Modifier.padding(start = 8.dp),
                                color = transactionCardItem.letterColor,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            )
            Column(
                modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = paymentAccountCardItem.icon),
                                        contentDescription = "Add button.",
                                        modifier = Modifier.size(48.dp),
                                        tint = paymentAccountCardItem.letterColor
                                    )
                                    Text(
                                        text = paymentAccountCardItem.typeName,
                                        modifier = Modifier.padding(start = 8.dp),
                                        color = paymentAccountCardItem.letterColor,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            )
                            Text(
                                text = paymentAccountCardItem.amount,
                                modifier = Modifier,
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
//                            .padding(16.dp),
                        content = {
                            Text(
                                modifier = Modifier,
                                text = paymentAccountCardItem.typeNameAccount,
                                color = paymentAccountCardItem.letterColor,
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