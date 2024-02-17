package com.soleel.paymentaccount.interfaces

import com.soleel.paymentaccount.model.PaymentAccount
import kotlinx.coroutines.flow.Flow

interface IPaymentAccountLocalDataSource {

    fun getPaymentAccounts(): Flow<List<PaymentAccount>>

//    suspend fun getPaymentAccounts(forceUpdate: Boolean = false): List<PaymentAccount>

    suspend fun refreshPaymentAccounts()

    fun getPaymentAccountStream(paymentAccountId: String): Flow<PaymentAccount?>

    suspend fun getPaymentAccount(
        paymentAccountId: String,
        forceUpdate: Boolean = false
    ): PaymentAccount?

    suspend fun refreshPaymentAccount(paymentAccountId: String)

    suspend fun createPaymentAccount(
        name: String,
        amount: Int,
        accountType: Int
    ): String

    suspend fun updatePaymentAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    )

    suspend fun deletePaymentAccount(paymentAccountId: String)

}