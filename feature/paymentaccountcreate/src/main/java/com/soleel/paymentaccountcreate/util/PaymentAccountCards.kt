package com.soleel.paymentaccountcreate.util

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

    fun getPaymentAccountCards(typePaymentAccount: Int): PaymentAccountCardItem {
        return when (typePaymentAccount) {
            PaymentAccountTypeConstant.CREDIT -> PaymentAccountCardItem(
                type = typePaymentAccount,
                typeName = "CREDITO",
                typeNameAccount = "Tarjeta de credito",
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
                typeNameAccount = "Tarjeta de debito",
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
                typeNameAccount = "Cuenta de ahorro",
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
                typeNameAccount = "Bolsa de inversion",
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
                    typeNameAccount = "Efectivo en bolsillo",
                    letterColor = CashLetterColor,
                    gradientBrush = paymentAccountCardLinearGradient(
                        CashGradientColor1,
                        CashGradientColor2
                    ),
                    icon = R.drawable.ic_money
                )
        }
    }

    val cardsList: List<PaymentAccountCardItem> = PaymentAccountTypeConstant.idToValueList.map(
        transform = { getPaymentAccountCards(it.first) }
    )
}

