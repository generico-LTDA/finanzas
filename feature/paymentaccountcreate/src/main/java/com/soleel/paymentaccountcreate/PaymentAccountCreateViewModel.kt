package com.soleel.paymentaccountcreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.validation.validator.AccountAmountValidator
import com.soleel.validation.validator.AccountTypeValidator
import com.soleel.validation.validator.NameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreatePaymentAccountUiCreate(
    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val accountType: Int = 0,
    val accountTypeError: Int? = null,

    val isPaymentAccountSaved: Boolean = false
)

sealed class CreatePaymentAccountUiEvent {
    data class NameChanged(val name: String) : CreatePaymentAccountUiEvent()
    data class AmountChanged(val amount: String) : CreatePaymentAccountUiEvent()
    data class AccountTypeChanged(val accountType: Int) : CreatePaymentAccountUiEvent()

    data object Submit : CreatePaymentAccountUiEvent()
}


@HiltViewModel
class CreatePaymentAccountViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource
) : ViewModel() {

    var createPaymentAccountUiCreate by mutableStateOf(CreatePaymentAccountUiCreate())

    private val nameValidator = NameValidator()
    private val accountAmountValidator = AccountAmountValidator()
    private val accountTypeValidator = AccountTypeValidator()

    fun onCreatePaymentAccountUiEvent(event: CreatePaymentAccountUiEvent) {
        when (event) {
            is CreatePaymentAccountUiEvent.AccountTypeChanged -> {
                createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(
                    accountType = event.accountType
                )
                validateAccountType()
            }

            is CreatePaymentAccountUiEvent.NameChanged -> {
                createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(name = event.name)
                validateName()
            }

            is CreatePaymentAccountUiEvent.AmountChanged -> {
                createPaymentAccountUiCreate =
                    createPaymentAccountUiCreate.copy(amount = event.amount)
                validateAmount()
            }

            is CreatePaymentAccountUiEvent.Submit -> {
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
            input = createPaymentAccountUiCreate.accountType
        )
        createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(
            accountTypeError = accountTypeResult.errorMessage
        )
        return accountTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = createPaymentAccountUiCreate.name)
        createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult =
            accountAmountValidator.execute(input = createPaymentAccountUiCreate.amount)
        createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun savePaymentAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                paymentAccountRepository.createPaymentAccount(
                    name = createPaymentAccountUiCreate.name,
                    amount = createPaymentAccountUiCreate.amount.toInt(),
                    accountType = createPaymentAccountUiCreate.accountType
                )

                createPaymentAccountUiCreate = createPaymentAccountUiCreate.copy(
                    isPaymentAccountSaved = true
                )
            })
    }
}
