package com.soleel.createtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.common.result.Result
import com.soleel.common.result.asResult
import com.soleel.validation.validator.AmountValidator
import com.soleel.validation.validator.CategoryTypeValidator
import com.soleel.validation.validator.DescriptionValidator
import com.soleel.validation.validator.NameValidator
import com.soleel.validation.validator.PaymentAccountTypeValidator
import com.soleel.validation.validator.TransactionTypeValidator
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.common.retryflow.RetryableFlowTrigger
import com.soleel.common.retryflow.retryableFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class CreateTransactionUiCreate(
    val name: String = "",
    val nameError: Int? = null,
    val amount: Int = 0,
    val amountError: Int? = null,
    val description: String = "",
    val descriptionError: Int? = null,
    val categoryType: Int = 0,
    val categoryTypeError: Int? = null,
    val transactionType: Int = 0,
    val transactionTypeError: Int? = null,
    val paymentAccountId: String = "",
    val paymentAccountIdError: Int? = null,

    val isTransactionSaved: Boolean = false
)

sealed class CreateTransactionUiEvent {
    data class NameChanged(val name: String) : CreateTransactionUiEvent()
    data class AmountChanged(val amount: Int) : CreateTransactionUiEvent()
    data class DescriptionChanged(val description: String) : CreateTransactionUiEvent()
    data class CategoryTypeChanged(val categoryType: Int) : CreateTransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : CreateTransactionUiEvent()
    data class PaymentAccountChanged(val paymentAccountId: String) : CreateTransactionUiEvent()
    data object Submit : CreateTransactionUiEvent()
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
class CreateTransactionViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    var createTransactionUiCreate by mutableStateOf(CreateTransactionUiCreate())

    private val nameValidator = NameValidator()
    private val amountValidator = AmountValidator()
    private val descriptionValidator = DescriptionValidator()
    private val categoryTypeValidator = CategoryTypeValidator()
    private val transactionTypeValidator = TransactionTypeValidator()
    private val validatePaymentAccountIdUseCase = PaymentAccountTypeValidator()

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

    fun onCreateTransactionUiEvent(event: CreateTransactionUiEvent) {
        when (event) {
            is CreateTransactionUiEvent.NameChanged -> {
                createTransactionUiCreate = createTransactionUiCreate.copy(name = event.name)
                validateName()
            }

            is CreateTransactionUiEvent.AmountChanged -> {
                createTransactionUiCreate =
                    createTransactionUiCreate.copy(amount = event.amount)
                validateAmount()
            }

            is CreateTransactionUiEvent.DescriptionChanged -> {
                createTransactionUiCreate = createTransactionUiCreate.copy(
                    description = event.description
                )
                validateDescription()
            }

            is CreateTransactionUiEvent.CategoryTypeChanged -> {
                createTransactionUiCreate = createTransactionUiCreate.copy(
                    categoryType = event.categoryType
                )
                validateCategoryType()
            }

            is CreateTransactionUiEvent.TransactionTypeChanged -> {
                createTransactionUiCreate = createTransactionUiCreate.copy(
                    transactionType = event.transactionType
                )
                validateTransactionType()
            }

            is CreateTransactionUiEvent.PaymentAccountChanged -> {
                createTransactionUiCreate = createTransactionUiCreate.copy(
                    paymentAccountId = event.paymentAccountId
                )
                validatePaymentAccountId()
            }

            is CreateTransactionUiEvent.Submit -> {
                if (validateName() && validateAmount() && validateDescription() && validateCategoryType() && validateTransactionType() && validatePaymentAccountId()) {
                    saveTransaction()
                }
            }
        }
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = createTransactionUiCreate.name)
        createTransactionUiCreate = createTransactionUiCreate.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult = amountValidator.execute(input = createTransactionUiCreate.amount)
        createTransactionUiCreate = createTransactionUiCreate.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun validateDescription(): Boolean {
        val descriptionResult = descriptionValidator.execute(
            input = createTransactionUiCreate.description
        )
        createTransactionUiCreate = createTransactionUiCreate.copy(
            descriptionError = descriptionResult.errorMessage
        )
        return descriptionResult.successful
    }

    private fun validateCategoryType(): Boolean {
        val categoryTypeResult = categoryTypeValidator.execute(
            input = createTransactionUiCreate.categoryType
        )
        createTransactionUiCreate = createTransactionUiCreate.copy(
            categoryTypeError = categoryTypeResult.errorMessage
        )
        return categoryTypeResult.successful
    }

    private fun validateTransactionType(): Boolean {
        val transactionTypeResult = transactionTypeValidator.execute(
            input = createTransactionUiCreate.transactionType
        )
        createTransactionUiCreate = createTransactionUiCreate.copy(
            transactionTypeError = transactionTypeResult.errorMessage
        )
        return transactionTypeResult.successful
    }

    private fun validatePaymentAccountId(): Boolean {
        val paymentAccountIdResult = validatePaymentAccountIdUseCase.execute(
            input = createTransactionUiCreate.paymentAccountId
        )
        createTransactionUiCreate = createTransactionUiCreate.copy(
            paymentAccountIdError = paymentAccountIdResult.errorMessage
        )
        return paymentAccountIdResult.successful
    }

    private fun saveTransaction() {

    }
}

