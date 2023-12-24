package com.soleel.common.constants

object AccountTypeConstant {
    const val CREDIT: Int = 1
    const val CREDIT_VALUE: String = "Credito"

    const val DEBIT: Int = 2
    const val DEBIT_VALUE: String = "Debito"

    const val SAVING: Int = 3
    const val SAVING_VALUE: String = "Ahorro"

    const val INVESTMENT: Int = 4
    const val INVESTMENT_VALUE: String = "Inversion"

    const val CASH: Int = 5
    const val CASH_VALUE: String = "Efectivo"

    val accountTypes: List<Pair<Int, String>> = listOf(
        CREDIT to CREDIT_VALUE,
        DEBIT to DEBIT_VALUE,
        SAVING to SAVING_VALUE,
        INVESTMENT to INVESTMENT_VALUE,
        CASH to CASH_VALUE
    )

}

