package com.soleel.ui.state

data class PaymentAccountCreateUi(
    val type: Int = 0,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val isPaymentAccountSaved: Boolean = false
)