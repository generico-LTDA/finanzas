package com.soleel.validation.validator

import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation
import com.soleel.ui.R


class DescriptionValidator : InValidation<String, ResultValidation> {
    override fun execute(input: String): ResultValidation {
        if (input.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.name_can_not_be_blank_error_message
            )
        }

        if (input.length < 12) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.description_too_short_error_message
            )
        }

        if (input.length > 64) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.description_too_long_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }

}