package com.soleel.paymentaccount.di

import com.soleel.paymentaccount.PaymentAccountRepository
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface PaymentAccountModule {

    @Singleton
    @Binds
    fun bindPaymentAccountLocalRepository(localRepository: PaymentAccountRepository) : IPaymentAccountLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindTransactionRemoteDataSource(remoteRepository: PaymentAccountRepository): IPaymentAccountRemoteDataSource

}