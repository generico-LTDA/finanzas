package com.soleel.validation.validator

import android.util.Log
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.validation.model.ResultValidation
import com.soleel.ui.R
import com.soleel.validation.generic.InValidation


class AmountValidator : InValidation<Triple<String, Int, Int>, ResultValidation> {

    companion object {
        const val maxCharLimit: Int = 8
        const val maxAmountLimit: Int = 9999999
    }

    override fun execute(input: Triple<String, Int, Int>): ResultValidation {

        if (0 == input.second) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.payment_account_not_selected_error_message
            )
        }

        if (input.first.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_blank_error_message
            )
        }

//        if (0 == input.toInt()) {
//            return ResultValidation(
//                successful = false,
//                errorMessage = R.string.amount_can_not_be_zero_error_message
//            )
//        }
//
//        if (0 > input.toInt()) {
//            return ResultValidation(
//                successful = false,
//                errorMessage = R.string.amount_can_not_be_negative_error_message
//            )
//        }

        if (maxAmountLimit < input.first.toInt()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeConstant.INCOME == input.third
            && maxAmountLimit < input.first.toInt() + input.second
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_more_payment_account_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeConstant.EXPEDITURE == input.third
            && 0 > input.second - input.first.toInt()
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_payment_account_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }
}