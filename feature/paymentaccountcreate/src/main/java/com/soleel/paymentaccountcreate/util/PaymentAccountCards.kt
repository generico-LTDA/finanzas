package com.soleel.paymentaccountcreate.util

import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.ui.util.PaymentAccountCardItem
import com.soleel.ui.util.getPaymentAccountCard

object PaymentAccountCards {
    val cardsList: List<PaymentAccountCardItem> = PaymentAccountTypeConstant.idToValueList.map(
        transform = { getPaymentAccountCard(paymentAccountType = it.first) }
    )
}

