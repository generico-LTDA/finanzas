package com.soleel.ui.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
            amount = amount ?: "$1,000,000",
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
            amount = amount ?: "$1,000,000",
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
            amount = amount ?: "$1,000,000",
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
            amount = amount ?: "$1,000,000",
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
                amount = amount ?: "$1,000,000",
                letterColor = CashLetterColor,
                gradientBrush = paymentAccountCardLinearGradient(
                    CashGradientColor1,
                    CashGradientColor2
                ),
                icon = R.drawable.ic_money
            )
    }
}