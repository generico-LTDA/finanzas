package com.soleel.ui.util

import androidx.compose.ui.graphics.Color
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.ui.R
import com.soleel.ui.theme.TypeTransactionExpeditureBackgroundColor
import com.soleel.ui.theme.TypeTransactionIncomeBackgroundColor
import com.soleel.ui.theme.TypeTransactionLetterColor


data class TransactionCardItem(
    val type: Int,
    val typeName: String,
    val typeIcon: Int,
    val typeBackgroundColor: Color,
    val letterColor: Color
)

fun getTransactionCard(
    transactionType: Int,
    nameTransactionType: String,
): TransactionCardItem {
    return when (transactionType) {
        TransactionTypeConstant.INCOME -> TransactionCardItem(
            type = transactionType,
            typeName = nameTransactionType,
            typeIcon = R.drawable.ic_income,
            typeBackgroundColor = TypeTransactionIncomeBackgroundColor,
            letterColor = TypeTransactionLetterColor
        )

        TransactionTypeConstant.EXPENDITURE -> TransactionCardItem(
            type = transactionType,
            typeName = nameTransactionType,
            typeIcon = R.drawable.ic_expediture,
            typeBackgroundColor = TypeTransactionExpeditureBackgroundColor,
            letterColor = TypeTransactionLetterColor
        )

        else -> TransactionCardItem(
            type = transactionType,
            typeName = "",
            typeIcon = R.drawable.ic_home,
            typeBackgroundColor = Color.Gray,
            letterColor = TypeTransactionLetterColor
        )
    }
}