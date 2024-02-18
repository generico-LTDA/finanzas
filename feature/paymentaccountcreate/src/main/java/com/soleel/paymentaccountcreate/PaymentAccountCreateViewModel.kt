package com.soleel.paymentaccountcreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.ui.state.PaymentAccountCreateEventUi
import com.soleel.ui.state.PaymentAccountCreateUi
import com.soleel.validation.validator.AccountAmountValidator
import com.soleel.validation.validator.AccountTypeValidator
import com.soleel.validation.validator.NameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PaymentAccountCreateViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource
) : ViewModel() {

    var paymentAccountCreateUi by mutableStateOf(PaymentAccountCreateUi())

    private val nameValidator = NameValidator()
    private val accountAmountValidator = AccountAmountValidator()
    private val accountTypeValidator = AccountTypeValidator()

    fun onPaymentAccountCreateEventUi(event: PaymentAccountCreateEventUi) {
        when (event) {
            is PaymentAccountCreateEventUi.AccountTypeChangedUi -> {
                paymentAccountCreateUi = paymentAccountCreateUi.copy(
                    accountType = event.accountType
                )
                validateAccountType()
            }

            is PaymentAccountCreateEventUi.NameChanged -> {
                paymentAccountCreateUi = paymentAccountCreateUi.copy(name = event.name)
                validateName()
            }

            is PaymentAccountCreateEventUi.AmountChanged -> {
                paymentAccountCreateUi =
                    paymentAccountCreateUi.copy(amount = event.amount)
                validateAmount()
            }

            is PaymentAccountCreateEventUi.Submit -> {
                if (validateAccountType()
                    && validateName()
                    && validateAmount()
                ) {
                    savePaymentAccount()
                }
            }
        }
    }

    private fun validateAccountType(): Boolean {
        val accountTypeResult = accountTypeValidator.execute(
            input = paymentAccountCreateUi.accountType
        )
        paymentAccountCreateUi = paymentAccountCreateUi.copy(
            accountTypeError = accountTypeResult.errorMessage
        )
        return accountTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = paymentAccountCreateUi.name)
        paymentAccountCreateUi = paymentAccountCreateUi.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult =
            accountAmountValidator.execute(input = paymentAccountCreateUi.amount)
        paymentAccountCreateUi = paymentAccountCreateUi.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun savePaymentAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                paymentAccountRepository.createPaymentAccount(
                    name = paymentAccountCreateUi.name,
                    amount = paymentAccountCreateUi.amount.toInt(),
                    accountType = paymentAccountCreateUi.accountType
                )

                paymentAccountCreateUi = paymentAccountCreateUi.copy(
                    isPaymentAccountSaved = true
                )
            })
    }
}
