package com.soleel.paymentaccountcreate.util

import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.ui.template.PaymentAccountCardItem
import com.soleel.ui.template.getPaymentAccountCard

object PaymentAccountCards {
    val cardsList: List<PaymentAccountCardItem> = PaymentAccountTypeConstant.idToValueList.map(
        transform = { getPaymentAccountCard(typePaymentAccount = it.first) }
    )
}

