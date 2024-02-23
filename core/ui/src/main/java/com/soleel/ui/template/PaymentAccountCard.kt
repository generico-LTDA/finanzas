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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.ui.R
import com.soleel.ui.theme.CashGradientColor1
import com.soleel.ui.theme.CashGradientColor2
import com.soleel.ui.theme.CashLetterColor
import com.soleel.ui.theme.CreditGradientColor1
import com.soleel.ui.theme.CreditGradientColor2
import com.soleel.ui.theme.CreditLetterColor
import com.soleel.ui.theme.DebitGradientColor1
import com.soleel.ui.theme.DebitGradientColor2
import com.soleel.ui.theme.DebitLetterColor
import com.soleel.ui.theme.InvestmentGradientColor1
import com.soleel.ui.theme.InvestmentGradientColor2
import com.soleel.ui.theme.InvestmentLetterColor
import com.soleel.ui.theme.SavingGradientColor1
import com.soleel.ui.theme.SavingGradientColor2
import com.soleel.ui.theme.SavingLetterColor
import com.soleel.ui.util.paymentAccountCardLinearGradient


@Composable
fun PaymentAccountCard(
    paymentAccountCardItem: PaymentAccountCardItem,
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
                modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = paymentAccountCardItem.typeName,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = paymentAccountCardItem.amount,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .padding(16.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = paymentAccountCardItem.icon),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = paymentAccountCardItem.letterColor
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                content = {
                                    Text(
                                        modifier = Modifier,
                                        text = "1234 5678 9012 3456",
                                        color = paymentAccountCardItem.letterColor,
                                        style = MaterialTheme.typography.headlineLarge,
                                        textAlign = TextAlign.Center
                                    )
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
    )
}

data class PaymentAccountCardItem(
    val type: Int,
    val typeName: String,
    var typeNameAccount: String,
    var amount: String,
    val letterColor: Color,
    val gradientBrush: Brush,
    val icon: Int
)

fun getPaymentAccountCard(
    typePaymentAccount: Int,
    nameAccount: String? = null,
    amount: String? = null,
): PaymentAccountCardItem {
    return when (typePaymentAccount) {
        PaymentAccountTypeConstant.CREDIT -> PaymentAccountCardItem(
            type = typePaymentAccount,
            typeName = "CREDITO",
            typeNameAccount = nameAccount ?: "Tarjeta de credito",
            amount = amount?: "$1,000,000",
            letterColor = CreditLetterColor,
            gradientBrush = paymentAccountCardLinearGradient(
                CreditGradientColor1,
                CreditGradientColor2
            ),
            icon = R.drawable.ic_credit
        )

        PaymentAccountTypeConstant.DEBIT -> PaymentAccountCardItem(
            type = typePaymentAccount,
            typeName = "DEBITO",
            typeNameAccount = nameAccount ?: "Tarjeta de debito",
            amount = amount?: "$1,000,000",
            letterColor = DebitLetterColor,
            gradientBrush = paymentAccountCardLinearGradient(
                DebitGradientColor1,
                DebitGradientColor2
            ),
            icon = R.drawable.ic_debit
        )


        PaymentAccountTypeConstant.SAVING -> PaymentAccountCardItem(
            type = typePaymentAccount,
            typeName = "AHORRO",
            typeNameAccount = nameAccount ?: "Cuenta de ahorro",
            amount = amount?: "$1,000,000",
            letterColor = SavingLetterColor,
            gradientBrush = paymentAccountCardLinearGradient(
                SavingGradientColor1,
                SavingGradientColor2
            ),
            icon = R.drawable.ic_saving
        )


        PaymentAccountTypeConstant.INVESTMENT -> PaymentAccountCardItem(
            type = typePaymentAccount,
            typeName = "INVERSION",
            typeNameAccount = nameAccount ?: "Bolsa de inversion",
            amount = amount?: "$1,000,000",
            letterColor = InvestmentLetterColor,
            gradientBrush = paymentAccountCardLinearGradient(
                InvestmentGradientColor1,
                InvestmentGradientColor2
            ),
            icon = R.drawable.ic_investment
        )

//            PaymentAccountTypeConstant.CASH ->
        else ->
            PaymentAccountCardItem(
                type = typePaymentAccount,
                typeName = "EFECTIVO",
                typeNameAccount = nameAccount ?: "Efectivo en bolsillo",
                amount = amount?: "$1,000,000",
                letterColor = CashLetterColor,
                gradientBrush = paymentAccountCardLinearGradient(
                    CashGradientColor1,
                    CashGradientColor2
                ),
                icon = R.drawable.ic_money
            )
    }
}