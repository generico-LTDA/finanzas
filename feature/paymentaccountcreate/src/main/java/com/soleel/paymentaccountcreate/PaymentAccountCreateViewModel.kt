package com.soleel.paymentaccountcreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.common.constants.CategoryTypeConstant
import com.soleel.common.constants.TransactionTypeConstant
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.validation.validator.AccountAmountValidator
import com.soleel.validation.validator.AccountTypeValidator
import com.soleel.validation.validator.NameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PaymentAccountCreateUi(
    val type: Int = 0,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val isPaymentAccountSaved: Boolean = false
)

sealed class PaymentAccountCreateEventUi {
    data class NameChanged(val name: String) : PaymentAccountCreateEventUi()
    data class AmountChanged(val amount: String) : PaymentAccountCreateEventUi()
    data class AccountTypeChangedUi(val accountType: Int) : PaymentAccountCreateEventUi()

    data object Submit : PaymentAccountCreateEventUi()
}

@HiltViewModel
class PaymentAccountCreateViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource
) : ViewModel() {

    var paymentAccountCreateUi by mutableStateOf(PaymentAccountCreateUi())

    private val accountTypeValidator = AccountTypeValidator()
    private val nameValidator = NameValidator()
    private val accountAmountValidator = AccountAmountValidator()

    fun onPaymentAccountCreateEventUi(event: PaymentAccountCreateEventUi) {
        when (event) {
            is PaymentAccountCreateEventUi.AccountTypeChangedUi -> {
                paymentAccountCreateUi = paymentAccountCreateUi.copy(
                    type = event.accountType
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
            input = paymentAccountCreateUi.type
        )
        paymentAccountCreateUi = paymentAccountCreateUi.copy(
            typeError = accountTypeResult.errorMessage
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
                val paymentAccountId = paymentAccountRepository.createPaymentAccount(
                    name = paymentAccountCreateUi.name,
                    amount = paymentAccountCreateUi.amount.toInt(),
                    accountType = paymentAccountCreateUi.type
                )

                transactionRepository.createTransaction(
                    name = "Monto inicial",
                    amount =paymentAccountCreateUi.amount.toInt(),
                    transactionType = TransactionTypeConstant.INCOME,
                    categoryType = CategoryTypeConstant.INCOME_TRANSFER,
                    paymentAccountId = paymentAccountId
                )

                paymentAccountCreateUi = paymentAccountCreateUi.copy(
                    isPaymentAccountSaved = true
                )
            })
    }
}
