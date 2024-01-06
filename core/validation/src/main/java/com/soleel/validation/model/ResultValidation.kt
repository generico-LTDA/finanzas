package com.soleel.validation.model

import com.soleel.ui.UiText


data class ResultValidation(
    val successful: Boolean,
    val errorMessage: UiText? = null
)