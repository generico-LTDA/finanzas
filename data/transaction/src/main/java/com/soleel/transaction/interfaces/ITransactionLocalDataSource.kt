package com.soleel.transaction.interfaces

import com.soleel.transaction.model.Transaction
import kotlinx.coroutines.flow.Flow

interface ITransactionLocalDataSource {

    fun getTransactions(): Flow<List<Transaction>>

//    suspend fun getTransactions(forceUpdate: Boolean = false): List<Transaction>

    suspend fun refreshTransactions()

    fun getTransactionStream(transactionId: String): Flow<Transaction?>

    suspend fun getTransaction(transactionId: String, forceUpdate: Boolean = false): Transaction?

    suspend fun refreshTransaction(transactionId: String)

    suspend fun createTransaction(
        name: String,
        amount: Int,
        transactionType: Int,
        categoryType: Int,
        paymentAccountId: String
    ): String

    suspend fun updateTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        paymentAccountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    )

    suspend fun deleteAllTransactions(paymentAccountId: String)

    suspend fun deleteTransaction(transactionId: String)
}