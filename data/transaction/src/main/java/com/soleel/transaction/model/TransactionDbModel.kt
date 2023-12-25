package com.soleel.transaction.model

import com.soleel.database.entities.TransactionEntity


class TransactionDbModel {
    companion object {

        fun asExternalModel(transactionEntity: TransactionEntity): Transaction {
            return Transaction(
                id = transactionEntity.id,
                name = transactionEntity.name,
                amount = transactionEntity.amount,
                description = transactionEntity.description,
                createAt = transactionEntity.createAt,
                updatedAt = transactionEntity.updatedAt,
                categoryType = transactionEntity.categoryType,
                transactionType = transactionEntity.transactionType,
                paymentAccountId = transactionEntity.paymentAccountId
            )
        }

        fun asExternalModelList(transactionEntities: List<TransactionEntity>): List<Transaction> {
            return transactionEntities.map(::asExternalModel)
        }
    }
}