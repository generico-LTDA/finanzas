package com.soleel.ui.template

import androidx.compose.foundation.background
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
import com.soleel.ui.R
import com.soleel.ui.theme.CreditGradientColor1
import com.soleel.ui.theme.CreditGradientColor2
import com.soleel.ui.theme.CreditLetterColor
import com.soleel.ui.util.PaymentAccountCardItem
import com.soleel.ui.util.paymentAccountCardLinearGradient


@Preview
@Composable
fun PaymentAccountMiniCardPreview() {
    PaymentAccountMiniCard(
        PaymentAccountCardItem(
            type = PaymentAccountTypeConstant.CREDIT,
            typeName = "CREDITO",
            typeNameAccount = "Tarjeta de credito",
            amount = "$1,000,000",
            letterColor = CreditLetterColor,
            gradientBrush = paymentAccountCardLinearGradient(
                CreditGradientColor1,
                CreditGradientColor2
            ),
            icon = R.drawable.ic_credit
        )
    )
}

@Composable
fun PaymentAccountMiniCard(
    paymentAccountCardItem: PaymentAccountCardItem,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            Column(
                modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                            .padding(16.dp),
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