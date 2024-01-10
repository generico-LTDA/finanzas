package com.soleel.validation.validator

import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation
import com.soleel.ui.R

class PaymentAccountTypeValidator : InValidation<String, ResultValidation> {

    override fun execute(input: String): ResultValidation {
        if (input.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.payment_account_not_selected_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }

}