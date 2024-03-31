package com.soleel.ui.util

import androidx.compose.ui.graphics.Color
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.ui.R
import com.soleel.ui.theme.TransactionTypeExpenditureBackgroundColor
import com.soleel.ui.theme.TransactionTypeIncomeBackgroundColor
import com.soleel.ui.theme.TransactionTypeLetterColor


data class TransactionTypeCardItem(
    val type: Int,
    val typeName: String,
    val typeIcon: Int,
    val typeBackgroundColor: Color,
    val letterColor: Color
)

fun getTransactionTypeCard(
    transactionType: Int,
//    transactionTypeName: String,
): TransactionTypeCardItem {
    return when (transactionType) {
        TransactionTypeConstant.INCOME -> TransactionTypeCardItem(
            type = TransactionTypeConstant.INCOME,
            typeName = TransactionTypeConstant.INCOME_VALUE,
            typeIcon = R.drawable.ic_income,
            typeBackgroundColor = TransactionTypeIncomeBackgroundColor,
            letterColor = TransactionTypeLetterColor
        )

        TransactionTypeConstant.EXPENDITURE -> TransactionTypeCardItem(
            type = TransactionTypeConstant.EXPENDITURE,
            typeName = TransactionTypeConstant.EXPENDITURE_VALUE,
            typeIcon = R.drawable.ic_expenditure,
            typeBackgroundColor = TransactionTypeExpenditureBackgroundColor,
            letterColor = TransactionTypeLetterColor
        )

        else -> TransactionTypeCardItem(
            type = transactionType,
            typeName = "",
            typeIcon = R.drawable.ic_home,
            typeBackgroundColor = Color.Gray,
            letterColor = TransactionTypeLetterColor
        )
    }
}