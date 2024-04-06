package com.soleel.transaction.di

import com.soleel.transaction.TransactionRepository
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {

    @Singleton
    @Binds
    fun bindTransactionLocalRepository(localRepository: TransactionRepository): ITransactionLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindTransactionRemoteDataSource(remoteRepository: TransactionRepository): ITransactionRemoteDataSource

}