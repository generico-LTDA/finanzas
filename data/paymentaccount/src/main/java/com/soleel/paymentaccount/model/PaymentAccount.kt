package com.soleel.paymentaccount.model

data class PaymentAccount(
    val id: String,
    val name: String,
    val initialAmount: Int,
    val createAt: Long,
    val updatedAt: Long,
    val accountType: Int
)