package com.soleel.validation.model


data class ResultValidation(
    val successful: Boolean,
    val errorMessage: Int? = null
)