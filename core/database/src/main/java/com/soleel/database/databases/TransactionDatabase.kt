package com.soleel.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase

import com.soleel.database.daos.PaymentAccountDAO
import com.soleel.database.daos.TransactionDAO
import com.soleel.database.entities.PaymentAccountEntity
import com.soleel.database.entities.TransactionEntity


@Database(
    entities = [
        PaymentAccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun paymentAccountDAO(): PaymentAccountDAO
    abstract fun transactionDAO(): TransactionDAO

}