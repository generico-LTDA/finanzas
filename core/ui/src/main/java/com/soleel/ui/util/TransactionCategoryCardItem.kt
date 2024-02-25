package com.soleel.ui.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.ui.R
import com.soleel.ui.theme.TransactionTypeLetterColor


data class TransactionCategoryCardItem(
    val category: Int,
    val categoryName: String,
    val categoryIcon: Int,
    val categoryNameTransaction: String,
    var amount: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getTransactionCategoryCard(
    transactionCategory: Int,
    transactionCategoryName: String,
    transactionCategoryNameTransaction: String? = null,
    amount: String? = null
): TransactionCategoryCardItem {
    TODO("AQUI ME QUEDE")
    return when (transactionCategory) {
        TransactionTypeConstant.INCOME -> getTransactionCategoryIncomeCard(
            transactionCategory = transactionCategory,
            transactionCategoryName = transactionCategoryName,
            transactionCategoryNameTransaction = transactionCategoryNameTransaction,
            amount = amount
        )

        TransactionTypeConstant.EXPENDITURE -> getTransactionCategoryExpenditureCard(
            transactionCategory = transactionCategory,
            transactionCategoryName = transactionCategoryName,
            transactionCategoryNameTransaction = transactionCategoryNameTransaction,
            amount = amount
        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = transactionCategoryName,
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionCategoryNameTransaction ?: "Una transaccion de prueba",
            amount = amount ?: "$10,000",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}

private fun getTransactionCategoryIncomeCard(
    transactionCategory: Int,
    transactionCategoryName: String,
    transactionCategoryNameTransaction: String? = null,
    amount: String?,
): TransactionCategoryCardItem {
    return when (transactionCategory) {
        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = transactionCategoryName,
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
            amount = amount ?: "$10,000",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}

private fun getTransactionCategoryExpenditureCard(
    transactionCategory: Int,
    transactionCategoryName: String,
    transactionCategoryNameTransaction: String? = null,
    amount: String?,
): TransactionCategoryCardItem {
    return when (transactionCategory) {
        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = transactionCategoryName,
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de gasto",
            amount = amount ?: "$10,000",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}