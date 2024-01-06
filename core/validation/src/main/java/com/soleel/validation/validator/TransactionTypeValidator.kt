package com.soleel.validation.validator

import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation
import com.soleel.ui.R
import com.soleel.ui.UiText

class TransactionTypeValidator : InValidation<Int, ResultValidation> {

    override fun execute(input: Int): ResultValidation {

        if (input == 0) {
            return ResultValidation(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.transaction_type_not_selected_error_message)
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }
}