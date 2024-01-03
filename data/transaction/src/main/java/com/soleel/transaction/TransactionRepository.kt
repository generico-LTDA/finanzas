package com.soleel.transaction

import com.soleel.database.daos.TransactionDAO
import com.soleel.transaction.di.DefaultDispatcher
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.transaction.model.Transaction
import com.soleel.transaction.model.TransactionDbModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ITransactionLocalDataSource {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDAO
            .getAllTransaction()
            .map(transform = TransactionDbModel::asExternalModelList)
    }

    override suspend fun refreshTransactions() {
        TODO("Not yet implemented")
    }

    override fun getTransactionStream(transactionId: String): Flow<Transaction?> {
        return transactionDAO
            .getTransactionForTransactionId(transactionId)
            .map(transform = TransactionDbModel::asExternalModel)
    }

    override suspend fun getTransaction(transactionId: String, forceUpdate: Boolean): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        paymentAccountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        paymentAccountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions(paymentAccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }
}