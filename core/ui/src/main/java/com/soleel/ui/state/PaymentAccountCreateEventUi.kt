package com.soleel.ui.state


sealed class PaymentAccountCreateEventUi {
    data class NameChanged(val name: String) : PaymentAccountCreateEventUi()
    data class AmountChanged(val amount: String) : PaymentAccountCreateEventUi()
    data class AccountTypeChangedUi(val accountType: Int) : PaymentAccountCreateEventUi()

    data object Submit : PaymentAccountCreateEventUi()
}