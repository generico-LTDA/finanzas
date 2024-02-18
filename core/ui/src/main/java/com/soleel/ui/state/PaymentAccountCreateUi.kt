package com.soleel.ui.state

data class PaymentAccountCreateUi(
    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val accountType: Int = 0,
    val accountTypeError: Int? = null,

    val isPaymentAccountSaved: Boolean = false
)