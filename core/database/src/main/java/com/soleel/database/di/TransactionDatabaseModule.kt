package com.soleel.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soleel.database.daos.PaymentAccountDAO
import com.soleel.database.daos.TransactionDAO
import com.soleel.database.databases.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionDatabaseModule {

    @Singleton
    @Provides
    fun provideTransactionDatabase(@ApplicationContext context: Context): TransactionDatabase {
        return Room.databaseBuilder(
            context,
            TransactionDatabase::class.java,
            "transaction_database.db"
        )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    fun providePaymentAccountDAO(database: TransactionDatabase): PaymentAccountDAO = database.paymentAccountDAO()

    @Provides
    fun provideTransactionDAO(database: TransactionDatabase): TransactionDAO = database.transactionDAO()

}
