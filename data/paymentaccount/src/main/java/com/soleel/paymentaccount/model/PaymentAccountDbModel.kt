package com.soleel.paymentaccount.model

import com.soleel.database.entities.PaymentAccountEntity


class PaymentAccountDbModel {
    companion object {
        fun asExternalModel(paymentAccountEntity: PaymentAccountEntity): PaymentAccount {
            return PaymentAccount(
                id = paymentAccountEntity.id,
                name = paymentAccountEntity.name,
                amount = paymentAccountEntity.amount,
                createAt = paymentAccountEntity.createAt,
                updatedAt = paymentAccountEntity.updatedAt,
                accountType = paymentAccountEntity.accountType,
            )
        }

        fun asExternalModelList(paymentAccountEntities: List<PaymentAccountEntity>): List<PaymentAccount> {
            return paymentAccountEntities.map(transform = ::asExternalModel)
        }

        fun asInternalModel(paymentAccount: PaymentAccount): PaymentAccountEntity {
            return PaymentAccountEntity(
                id = paymentAccount.id,
                name = paymentAccount.name,
                amount = paymentAccount.amount,
                createAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                accountType = paymentAccount.accountType
            );
        }
    }
}

