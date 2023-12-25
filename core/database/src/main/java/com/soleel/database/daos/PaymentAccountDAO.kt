package com.soleel.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.soleel.database.entities.PaymentAccountEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PaymentAccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paymentAccountEntity: PaymentAccountEntity)

    @Query("SELECT * FROM payment_account_table")
    fun getAllPaymentAccount(): Flow<List<PaymentAccountEntity>>

    @Query("SELECT * FROM payment_account_table WHERE id = :id")
    fun getPaymentAccountForPaymentAccountId(id: String): Flow<PaymentAccountEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(paymentAccountEntity: PaymentAccountEntity)

    @Delete
    suspend fun delete(paymentAccountEntity: PaymentAccountEntity)
}
