package com.soleel.common.constants

object TransactionTypeConstant {

    const val MARKET: Int = 1
    const val MARKET_VALUE: String = "Mercado"

    const val FAIR: Int = 2
    const val FAIR_VALUE: String = "Feria"

    const val SERVICE: Int = 3
    const val SERVICE_VALUE: String = "Servicio"

    const val HOME: Int = 4
    const val HOME_VALUE: String = "Casa"

    const val HEALTH: Int = 5
    const val HEALTH_VALUE: String = "Salud"

    const val TRANSPORT: Int = 6
    const val TRANSPORT_VALUE: String = "Transporte"

    const val LOAN: Int = 7
    const val LOAN_VALUE: String = "Prestamo"

    const val ACQUISITION: Int = 8
    const val ACQUISITION_VALUE: String = "Adquisicion"

    const val LEASURE: Int = 9
    const val LEASURE_VALUE: String = "Ocio"

    const val GIFT: Int = 10
    const val GIFT_VALUE: String = "Regalo"

    val idToValueList: List<Pair<Int, String>> = listOf(
        MARKET to MARKET_VALUE,
        FAIR to FAIR_VALUE,
        SERVICE to SERVICE_VALUE,
        HOME to HOME_VALUE,
        HEALTH to HEALTH_VALUE,
        TRANSPORT to TRANSPORT_VALUE,
        LOAN to LOAN_VALUE,
        ACQUISITION to ACQUISITION_VALUE,
        LEASURE to LEASURE_VALUE,
        GIFT to GIFT_VALUE)


    val idList: List<Int> = listOf(
        MARKET,
        FAIR,
        SERVICE,
        HOME,
        HEALTH,
        TRANSPORT,
        LOAN,
        ACQUISITION,
        LEASURE,
        GIFT)

}