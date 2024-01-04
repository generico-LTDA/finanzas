package com.soleel.transaction.model

data class Transaction(
    val id: String,
    val name: String,
    val amount: Int,
    val description: String,
    val createAt: Long,
    val updatedAt: Long,
    val categoryType: Int,
    val transactionType: Int,
    val paymentAccountId: String
)