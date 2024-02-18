package com.soleel.paymentaccountcreate.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.ui.R
import com.soleel.ui.template.PaymentAccountCardItem
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

object PaymentAccountCards {

    private fun getPaymentAccountCards(typePaymentAccount: Int): PaymentAccountCardItem {
        return when (typePaymentAccount) {
            PaymentAccountTypeConstant.CREDIT -> PaymentAccountCardItem(
                typePaymentAccount,
                "CREDITO",
                "Tarjeta de credito",
                CreditLetterColor,
                paymentAccountCardLinearGradient(CreditGradientColor1, CreditGradientColor2),
                R.drawable.ic_credit
            )

            PaymentAccountTypeConstant.DEBIT -> PaymentAccountCardItem(
                typePaymentAccount,
                "DEBITO",
                "Tarjeta de debito",
                DebitLetterColor,
                paymentAccountCardLinearGradient(DebitGradientColor1, DebitGradientColor2),
                R.drawable.ic_debit
            )


            PaymentAccountTypeConstant.SAVING -> PaymentAccountCardItem(
                typePaymentAccount,
                "AHORRO",
                "Cuenta de ahorro",
                SavingLetterColor,
                paymentAccountCardLinearGradient(SavingGradientColor1, SavingGradientColor2),
                R.drawable.ic_saving
            )


            PaymentAccountTypeConstant.INVESTMENT -> PaymentAccountCardItem(
                typePaymentAccount,
                "INVERSION",
                "Bolsa de inversion",
                InvestmentLetterColor,
                paymentAccountCardLinearGradient(
                    InvestmentGradientColor1,
                    InvestmentGradientColor2
                ),
                R.drawable.ic_investment
            )

//            PaymentAccountTypeConstant.CASH ->
            else ->
                PaymentAccountCardItem(
                    typePaymentAccount,
                    "EFECTIVO",
                    "Efectivo en bolsillo",
                    CashLetterColor,
                    paymentAccountCardLinearGradient(CashGradientColor1, CashGradientColor2),
                    R.drawable.ic_money
                )
        }
    }

    val cardsList: List<PaymentAccountCardItem> = PaymentAccountTypeConstant.idToValueList.map(
        transform = { getPaymentAccountCards(it.first) }
    )
}

