package com.soleel.common.constants

object CategoryTypeConstant {
    const val INCOME: Int = 1
    const val INCOME_VALUE: String = "Ingreso"

    const val EXPEDITURE: Int = 2
    const val EXPEDITURE_VALUE: String = "Gasto"

    val idToValueList: List<Pair<Int, String>> = listOf(
        INCOME to INCOME_VALUE,
        EXPEDITURE to EXPEDITURE_VALUE
    )

    val idList: List<Int> = listOf(INCOME, EXPEDITURE)

}