package com.soleel.validation.validator

import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation
import com.soleel.ui.R
import com.soleel.ui.UiText

class AmountValidator : InValidation<Int, ResultValidation> {
    override fun execute(input: Int): ResultValidation {
        if (input == 0) {
            return ResultValidation(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.amount_can_not_be_zero_error_message)
            )
        }

        if (input < 0) {
            return ResultValidation(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.amount_can_not_be_negative_error_message)
            )
        }

        if (input > 99999999) {
            return ResultValidation(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.amount_can_not_be_greater_than_error_message)
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }
}