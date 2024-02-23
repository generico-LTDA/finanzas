package com.soleel.transactioncreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.common.result.Result
import com.soleel.common.result.asResult
import com.soleel.common.retryflow.RetryableFlowTrigger
import com.soleel.common.retryflow.retryableFlow
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.validation.validator.CategoryTypeValidator
import com.soleel.validation.validator.NameValidator
import com.soleel.validation.validator.PaymentAccountTypeValidator
import com.soleel.validation.validator.TransactionAmountValidator
import com.soleel.validation.validator.TransactionTypeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TransactionUiCreate(
    val paymentAccount: PaymentAccount = PaymentAccount(
        id = "", name = "", amount = 0, createAt = 0, updatedAt = 0, accountType = 0
    ),
    val paymentAccountError: Int? = null,

    val transactionType: Int = 0,
    val transactionTypeError: Int? = null,

    val categoryType: Int = 0,
    val categoryTypeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val isTransactionSaved: Boolean = false
)

sealed class TransactionUiEvent {
    data class PaymentAccountChanged(val paymentAccount: PaymentAccount) : TransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : TransactionUiEvent()
    data class CategoryTypeChanged(val categoryType: Int) : TransactionUiEvent()
    data class NameChanged(val name: String) : TransactionUiEvent()
    data class AmountChanged(val amount: String) : TransactionUiEvent()

    data object Submit : TransactionUiEvent()
}

sealed interface PaymentAccountsUiState {
    data class Success(val paymentAccounts: List<PaymentAccount>) : PaymentAccountsUiState
    data object Error : PaymentAccountsUiState
    data object Loading : PaymentAccountsUiState
}

sealed class PaymentAccountsUiEvent {
    data object Retry : PaymentAccountsUiEvent()
}


@HiltViewModel
class TransactionCreateViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    var transactionUiCreate by mutableStateOf(TransactionUiCreate())

    private val validatePaymentAccountUseCase = PaymentAccountTypeValidator()
    private val transactionTypeValidator = TransactionTypeValidator()
    private val categoryTypeValidator = CategoryTypeValidator()
    private val nameValidator = NameValidator()
    private val amountValidator = TransactionAmountValidator()

    private val _paymentAccountsUiState: Flow<PaymentAccountsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = {
            paymentAccountUiState(paymentAccountRepository = paymentAccountRepository)
        })

    val paymentAccountsUiState: StateFlow<PaymentAccountsUiState> = _paymentAccountsUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = PaymentAccountsUiState.Loading
    )

    private fun paymentAccountUiState(
        paymentAccountRepository: IPaymentAccountLocalDataSource,
    ): Flow<PaymentAccountsUiState> {
        return paymentAccountRepository.getPaymentAccounts()
            .asResult()
            .map(transform = this::getData)
    }

//    private fun paymentAccountUiState(
//        paymentAccountRepository: IPaymentAccountLocalDataSource,
//    ): Flow<PaymentAccountsUiState> {
//        return flow {
//            emit(PaymentAccountsUiState.Loading)
//
//            delay(2000)
//
//            val itemsPaymentAccount = paymentAccountRepository.getPaymentAccounts()
//                .asResult()
//                .map {  getData(it)}
//
//            emitAll(itemsPaymentAccount)
//        }
//    }

    private fun getData(
        itemsPaymentAccount: Result<List<PaymentAccount>>
    ): PaymentAccountsUiState {
        return when (itemsPaymentAccount) {
            is Result.Success -> PaymentAccountsUiState.Success(itemsPaymentAccount.data)
            is Result.Error -> PaymentAccountsUiState.Error
            is Result.Loading -> PaymentAccountsUiState.Loading
        }
    }

    fun onPaymentAccountsUiEvent(event: PaymentAccountsUiEvent) {
        when (event) {
            is PaymentAccountsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

    fun onTransactionCreateUiEvent(event: TransactionUiEvent) {
        when (event) {
            is TransactionUiEvent.PaymentAccountChanged -> {
                // README: Campo objetivo
                transactionUiCreate = transactionUiCreate.copy(
                    paymentAccount = event.paymentAccount
                )

                // README: Campos afectados
                transactionUiCreate = transactionUiCreate.copy(
                    transactionType = 0,
                    transactionTypeError = null,
                    categoryType = 0,
                    categoryTypeError = null
                )

                validatePaymentAccount()
            }

            is TransactionUiEvent.TransactionTypeChanged -> {
                // README: Campo objetivo
                transactionUiCreate = transactionUiCreate.copy(
                    transactionType = event.transactionType
                )

                // README: Campos afectados
                transactionUiCreate = transactionUiCreate.copy(
                    categoryType = 0,
                    categoryTypeError = null
                )

                validateTransactionType()
            }

            is TransactionUiEvent.CategoryTypeChanged -> {
                transactionUiCreate = transactionUiCreate.copy(
                    categoryType = event.categoryType
                )
                validateCategoryType()
            }

            is TransactionUiEvent.NameChanged -> {
                transactionUiCreate = transactionUiCreate.copy(name = event.name)
                validateName()
            }

            is TransactionUiEvent.AmountChanged -> {
                transactionUiCreate = transactionUiCreate.copy(amount = event.amount)
                validateAmount()
            }

            is TransactionUiEvent.Submit -> {
                if (validatePaymentAccount()
                    && validateTransactionType()
                    && validateCategoryType()
                    && validateName()
                    && validateAmount()
                ) {
                    saveTransaction()
                }
            }
        }
    }

    private fun validatePaymentAccount(): Boolean {
        val paymentAccountResult = validatePaymentAccountUseCase.execute(
            input = transactionUiCreate.paymentAccount
        )
        transactionUiCreate = transactionUiCreate.copy(
            paymentAccountError = paymentAccountResult.errorMessage
        )
        return paymentAccountResult.successful
    }

    private fun validateTransactionType(): Boolean {
        val transactionTypeResult = transactionTypeValidator.execute(
            input = transactionUiCreate.transactionType
        )
        transactionUiCreate = transactionUiCreate.copy(
            transactionTypeError = transactionTypeResult.errorMessage
        )
        return transactionTypeResult.successful
    }

    private fun validateCategoryType(): Boolean {
        val categoryTypeResult = categoryTypeValidator.execute(
            input = transactionUiCreate.categoryType
        )
        transactionUiCreate = transactionUiCreate.copy(
            categoryTypeError = categoryTypeResult.errorMessage
        )
        return categoryTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = transactionUiCreate.name)
        transactionUiCreate = transactionUiCreate.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val input = Triple<String, Int, Int>(
            first = transactionUiCreate.amount,
            second = transactionUiCreate.paymentAccount.amount,
            third = transactionUiCreate.transactionType
        )

        val amountResult = amountValidator.execute(input = input)

        transactionUiCreate = transactionUiCreate.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun saveTransaction() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                transactionRepository.createTransaction(
                    name = transactionUiCreate.name,
                    amount = transactionUiCreate.amount.toInt(),
                    transactionType = transactionUiCreate.transactionType,
                    categoryType = transactionUiCreate.categoryType,
                    paymentAccountId = transactionUiCreate.paymentAccount.id
                )

                transactionUiCreate = transactionUiCreate.copy(
                    isTransactionSaved = true
                )
            })
    }
}

