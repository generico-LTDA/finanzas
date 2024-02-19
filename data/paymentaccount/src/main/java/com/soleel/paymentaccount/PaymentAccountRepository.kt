package com.soleel.paymentaccount

import com.soleel.database.daos.PaymentAccountDAO
import com.soleel.paymentaccount.di.DefaultDispatcher
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.paymentaccount.model.PaymentAccountDbModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class PaymentAccountRepository @Inject constructor(
    private val paymentAccountDAO: PaymentAccountDAO,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : IPaymentAccountLocalDataSource {

    override fun getPaymentAccounts(): Flow<List<PaymentAccount>> {
        return paymentAccountDAO
            .getAllPaymentAccount()
            .map(transform = PaymentAccountDbModel::asExternalModelList)
    }

    override suspend fun refreshPaymentAccounts() {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountStream(paymentAccountId: String): Flow<PaymentAccount?> {
        return paymentAccountDAO
            .getPaymentAccountForPaymentAccountId(paymentAccountId)
            .map(transform = PaymentAccountDbModel::asExternalModel)
    }

    override suspend fun getPaymentAccount(
        paymentAccountId: String,
        forceUpdate: Boolean
    ): PaymentAccount? {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPaymentAccount(paymentAccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createPaymentAccount(
        name: String,
        amount: Int,
        accountType: Int
    ): String {
        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            })

        val paymentAccount = PaymentAccount(
            id = id,
            name = name,
            amount = amount,
            createAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            accountType = accountType
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                paymentAccountDAO.insert(PaymentAccountDbModel.asInternalModel(paymentAccount))
            }
        )

        return id
    }

    override suspend fun updatePaymentAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePaymentAccount(paymentAccountId: String) {
        TODO("Not yet implemented")
    }
}