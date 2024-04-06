package com.soleel.paymentaccount.model

data class PaymentAccount(
    val id: String,
    val name: String,
    var amount: Int,
    val createAt: Long,
    val updatedAt: Long,
    val accountType: Int
)