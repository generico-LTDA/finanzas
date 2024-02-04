package com.soleel.common.constants


object CategoryTypeConstant {

    const val INCOME_TRANSFER: Int = 11
    const val INCOME_TRANSFER_VALUE: String = "Transferencia"

    const val INCOME_SALARY: Int = 12
    const val INCOME_SALARY_VALUE: String = "Sueldo"

    const val INCOME_SERVICE: Int = 13
    const val INCOME_SERVICE_VALUE: String = "Servicio prestados"

    const val INCOME_SALES: Int = 14
    const val INCOME_SALES_VALUE: String = "Ventas"

    const val INCOME_BONUS: Int = 15
    const val INCOME_BONUS_VALUE: String = "Bono"
    // Considerar donacion como bono

    const val INCOME_REFOUND: Int = 16
    const val INCOME_REFOUND_VALUE: String = "Reembolso"

    const val INCOME_OTHER: Int = 19
    const val INCOME_OTHER_VALUE: String = "Otro"
    // Considerar pago de cuota y aumento de credito, como aumento del amount de la linea de credito

    const val EXPEDITURE_TRANSFER: Int = 21
    const val EXPEDITURE_TRANSFER_VALUE: String = "Transferencia"

    const val EXPEDITURE_MARKET: Int = 23
    const val EXPEDITURE_MARKET_VALUE: String = "Despensa"

    const val EXPEDITURE_SERVICE: Int = 24
    const val EXPEDITURE_SERVICE_VALUE: String = "Servicio obtenidos"
    // Considera: Transporte, Salud

    const val EXPEDITURE_ACQUISITION: Int = 25
    const val EXPEDITURE_ACQUISITION_VALUE: String = "Adquisicion"
    // Considera la compra de cosas para la casa

    const val EXPEDITURE_LEASURE: Int = 26
    const val EXPEDITURE_LEASURE_VALUE: String = "Ocio"

    const val EXPEDITURE_GIFT: Int = 27
    const val EXPEDITURE_GIFT_VALUE: String = "Regalo"

    const val EXPEDITURE_OTHER: Int = 29
    const val EXPEDITURE_OTHER_VALUE: String = "Otro"

    fun idToValueList(transactionType: Int, accountType: Int): List<Pair<Int, String>> {
        return when (transactionType) {
            TransactionTypeConstant.INCOME -> idIncomeToValueList(accountType)
            TransactionTypeConstant.EXPEDITURE -> idExpeditureToValueList(accountType)
            else -> listOf()
        }
    }

    private fun idIncomeToValueList(accountType: Int): List<Pair<Int, String>> {
        return when (accountType) {
            AccountTypeConstant.CREDIT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            AccountTypeConstant.DEBIT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_SALARY to INCOME_SALARY_VALUE,
                INCOME_SERVICE to INCOME_SERVICE_VALUE,
                INCOME_SALES to INCOME_SALES_VALUE,
                INCOME_BONUS to INCOME_BONUS_VALUE,
                INCOME_REFOUND to INCOME_REFOUND_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            AccountTypeConstant.SAVING,
            AccountTypeConstant.INVESTMENT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            AccountTypeConstant.CASH -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_SALARY to INCOME_SALARY_VALUE,
                INCOME_SERVICE to INCOME_SERVICE_VALUE,
                INCOME_SALES to INCOME_SALES_VALUE,
                INCOME_BONUS to INCOME_BONUS_VALUE,
                INCOME_REFOUND to INCOME_REFOUND_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            else -> listOf()
        }
    }

    private fun idExpeditureToValueList(accountType: Int): List<Pair<Int, String>> {
        return when (accountType) {
            AccountTypeConstant.CREDIT -> listOf(
                EXPEDITURE_MARKET to EXPEDITURE_MARKET_VALUE,
                EXPEDITURE_SERVICE to EXPEDITURE_SERVICE_VALUE,
                EXPEDITURE_ACQUISITION to EXPEDITURE_ACQUISITION_VALUE,
                EXPEDITURE_LEASURE to EXPEDITURE_LEASURE_VALUE,
                EXPEDITURE_GIFT to EXPEDITURE_GIFT_VALUE,
                EXPEDITURE_OTHER to EXPEDITURE_OTHER_VALUE
            )

            AccountTypeConstant.DEBIT -> listOf(
                EXPEDITURE_TRANSFER to EXPEDITURE_TRANSFER_VALUE,
                EXPEDITURE_MARKET to EXPEDITURE_MARKET_VALUE,
                EXPEDITURE_SERVICE to EXPEDITURE_SERVICE_VALUE,
                EXPEDITURE_ACQUISITION to EXPEDITURE_ACQUISITION_VALUE,
                EXPEDITURE_LEASURE to EXPEDITURE_LEASURE_VALUE,
                EXPEDITURE_GIFT to EXPEDITURE_GIFT_VALUE,
                EXPEDITURE_OTHER to EXPEDITURE_OTHER_VALUE
            )

            AccountTypeConstant.SAVING,
            AccountTypeConstant.INVESTMENT -> listOf(
                EXPEDITURE_TRANSFER to EXPEDITURE_TRANSFER_VALUE,
                EXPEDITURE_OTHER to EXPEDITURE_OTHER_VALUE
            )

            AccountTypeConstant.CASH -> listOf(
                EXPEDITURE_TRANSFER to EXPEDITURE_TRANSFER_VALUE,
                EXPEDITURE_MARKET to EXPEDITURE_MARKET_VALUE,
                EXPEDITURE_SERVICE to EXPEDITURE_SERVICE_VALUE,
                EXPEDITURE_ACQUISITION to EXPEDITURE_ACQUISITION_VALUE,
                EXPEDITURE_LEASURE to EXPEDITURE_LEASURE_VALUE,
                EXPEDITURE_GIFT to EXPEDITURE_GIFT_VALUE,
                EXPEDITURE_OTHER to EXPEDITURE_OTHER_VALUE
            )

            else -> listOf()
        }
    }

    val idExpeditureList: List<Int> = listOf(
        EXPEDITURE_TRANSFER,
        EXPEDITURE_MARKET,
        EXPEDITURE_SERVICE,
        EXPEDITURE_ACQUISITION,
        EXPEDITURE_LEASURE,
        EXPEDITURE_GIFT,
        EXPEDITURE_OTHER
    )
}