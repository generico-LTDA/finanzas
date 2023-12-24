package com.soleel.paymentaccount.model

import com.soleel.database.entities.PaymentAccountEntity


class PaymentAccountDbModel {
    companion object {
        fun asExternalModel(paymentAccountEntity: PaymentAccountEntity): PaymentAccount {
            return PaymentAccount(
                id = paymentAccountEntity.id,
                name = paymentAccountEntity.name,
                initialAmount = paymentAccountEntity.initialAmount,
                createAt = paymentAccountEntity.createAt,
                updatedAt = paymentAccountEntity.updatedAt,
                accountType = paymentAccountEntity.accountType,
            )
        }

        fun asExternalModelList(paymentAccountEntities: List<PaymentAccountEntity>): List<PaymentAccount> {
            return paymentAccountEntities.map(transform = Companion::asExternalModel)
        }

        fun asInternalModel(paymentAccount: PaymentAccount): PaymentAccountEntity {
            return PaymentAccountEntity(
                id = paymentAccount.id,
                name = paymentAccount.name,
                initialAmount = paymentAccount.initialAmount,
                createAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                accountType = paymentAccount.accountType
            );
        }
    }
}

