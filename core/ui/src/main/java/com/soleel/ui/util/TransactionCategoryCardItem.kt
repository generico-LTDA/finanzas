package com.soleel.ui.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.common.constants.TransactionCategoryConstant
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.ui.R
import com.soleel.ui.theme.CategoryIncomeTransferGradientColor1
import com.soleel.ui.theme.CategoryIncomeTransferGradientColor2
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
    transactionType: Int,
    transactionCategory: Int,
    transactionCategoryNameTransaction: String? = null,
    amount: String? = null
): TransactionCategoryCardItem {
    return when (transactionType) {
        TransactionTypeConstant.INCOME -> getTransactionCategoryIncomeCard(
            transactionCategory = transactionCategory,
            transactionCategoryNameTransaction = transactionCategoryNameTransaction,
            amount = amount
        )

        TransactionTypeConstant.EXPENDITURE -> getTransactionCategoryExpenditureCard(
            transactionCategory = transactionCategory,
            transactionCategoryNameTransaction = transactionCategoryNameTransaction,
            amount = amount
        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
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
    transactionCategoryNameTransaction: String? = null,
    amount: String?,
): TransactionCategoryCardItem {
    return when (transactionCategory) {
        TransactionCategoryConstant.INCOME_TRANSFER -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_TRANSFER_VALUE,
            categoryIcon = R.drawable.ic_income_transfer, // PROBLEMA DE CARGA CON ESTE ICONO
            categoryNameTransaction = transactionCategoryNameTransaction ?: "Ingreso por Transferencia",
            amount = amount ?: "$10,000",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(
                CategoryIncomeTransferGradientColor1,
                CategoryIncomeTransferGradientColor2
            )
        )
//
//        TransactionCategoryConstant.INCOME_SALARY  -> TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_SALARY_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )
//
//        TransactionCategoryConstant.INCOME_SERVICE  -> TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_SERVICE_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )
//
//        TransactionCategoryConstant.INCOME_SALES  -> TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_SALES_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )
//
//        TransactionCategoryConstant.INCOME_BONUS  -> TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_BONUS_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )
//
//        TransactionCategoryConstant.INCOME_REFOUND  ->  TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_REFOUND_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )
//
//        TransactionCategoryConstant.INCOME_OTHER  -> TransactionCategoryCardItem(
//            category = transactionCategory,
//            categoryName = TransactionCategoryConstant.INCOME_OTHER_VALUE,
//            categoryIcon = R.drawable.ic_debit,
//            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de ingreso",
//            amount = amount ?: "$10,000",
//            letterColor = TransactionTypeLetterColor,
//            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
//        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
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
    transactionCategoryNameTransaction: String? = null,
    amount: String?,
): TransactionCategoryCardItem {
    return when (transactionCategory) {
        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionCategoryNameTransaction ?: "Transaccion de gasto",
            amount = amount ?: "$10,000",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}