package com.soleel.createtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.ui.UiText
import com.soleel.common.result.Result
import com.soleel.common.result.asResult
import com.soleel.validation.validator.AmountValidator
import com.soleel.validation.validator.CategoryTypeValidator
import com.soleel.validation.validator.DescriptionValidator
import com.soleel.validation.validator.NameValidator
import com.soleel.validation.validator.PaymentAccountIdTypeValidator
import com.soleel.validation.validator.TransactionTypeValidator
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class CreateTransactionUiCreate(
    val name: String = "",
    val nameError: UiText? = null,
    val amount: Int = 0,
    val amountError: UiText? = null,
    val description: String = "",
    val descriptionError: UiText? = null,
    val categoryType: Int = 0,
    val categoryTypeError: UiText? = null,
    val transactionType: Int = 0,
    val transactionTypeError: UiText? = null,
    val paymentAccountId: String = "",
    val paymentAccountIdError: UiText? = null,

    val isTransactionSaved: Boolean = false
)

sealed class CreateTransactionUiEvent {
    data class NameChanged(val name: String) : CreateTransactionUiEvent()
    data class AmountChanged(val amount: Int) : CreateTransactionUiEvent()
    data class DescriptionChanged(val description: String) : CreateTransactionUiEvent()
    data class CategoryTypeChanged(val categoryType: Int) : CreateTransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : CreateTransactionUiEvent()
    data class PaymentAccountChanged(val paymentAccountId: String) : CreateTransactionUiEvent()
    object Submit : CreateTransactionUiEvent()
}

sealed interface PaymentAccountsUiState {
    data class Success(val paymentAccounts: List<PaymentAccount>) : PaymentAccountsUiState
    data object Error : PaymentAccountsUiState
    data object Loading : PaymentAccountsUiState
}

@HiltViewModel
class CreateTransactionViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource
) : ViewModel() {

//    private val _createTransactionUiState: MutableStateFlow<CreateTransactionUiState> =
//        MutableStateFlow(CreateTransactionUiState())
//    val createTransactionUiState: StateFlow<CreateTransactionUiState> =
//        _createTransactionUiState.asStateFlow()

    var createTransactionUiState by mutableStateOf(CreateTransactionUiCreate())

    private val nameValidator = NameValidator()
    private val amountValidator = AmountValidator()
    private val descriptionValidator = DescriptionValidator()
    private val categoryTypeValidator = CategoryTypeValidator()
    private val transactionTypeValidator = TransactionTypeValidator()
    private val validatePaymentAccountIdUseCase = PaymentAccountIdTypeValidator()

    private val _paymentAccountsUiState: Flow<PaymentAccountsUiState> = paymentAccountUiState(
        paymentAccountRepository = paymentAccountRepository
    )
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

    private fun getData(
        itemsPaymentAccount: Result<List<PaymentAccount>>
    ): PaymentAccountsUiState {
        return when (itemsPaymentAccount) {
            is Result.Success -> PaymentAccountsUiState.Success(itemsPaymentAccount.data)
            is Result.Loading -> PaymentAccountsUiState.Loading
            is Result.Error -> PaymentAccountsUiState.Error
        }
    }

    fun onEvent(event: CreateTransactionUiEvent) {
        when (event) {
            is CreateTransactionUiEvent.NameChanged -> {
                createTransactionUiState = createTransactionUiState.copy(name = event.name)
                validateName()
            }

            is CreateTransactionUiEvent.AmountChanged -> {
                createTransactionUiState = createTransactionUiState.copy(amount = event.amount)
                validateAmount()
            }

            is CreateTransactionUiEvent.DescriptionChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    description = event.description)
                validateDescription()
            }

            is CreateTransactionUiEvent.CategoryTypeChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    categoryType = event.categoryType)
                validateCategoryType()
            }

            is CreateTransactionUiEvent.TransactionTypeChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    transactionType = event.transactionType)
                validateTransactionType()
            }

            is CreateTransactionUiEvent.PaymentAccountChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    paymentAccountId = event.paymentAccountId)
                validatePaymentAccountId()
            }

            is CreateTransactionUiEvent.Submit -> {
                if (validateName()
                    && validateAmount()
                    && validateDescription()
                    && validateCategoryType()
                    && validateTransactionType()
                    && validatePaymentAccountId()
                ) {
                    saveTransaction()
                }
            }
        }
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(createTransactionUiState.name)
        createTransactionUiState = createTransactionUiState.copy(
            nameError = nameResult.errorMessage)
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult = amountValidator.execute(createTransactionUiState.amount)
        createTransactionUiState = createTransactionUiState.copy(
            amountError = amountResult.errorMessage)
        return amountResult.successful
    }

    private fun validateDescription(): Boolean {
        val descriptionResult = descriptionValidator.execute(
            createTransactionUiState.description)
        createTransactionUiState = createTransactionUiState.copy(
            descriptionError = descriptionResult.errorMessage)
        return descriptionResult.successful
    }

    private fun validateCategoryType(): Boolean {
        val categoryTypeResult = categoryTypeValidator.execute(
            input = createTransactionUiState.categoryType)
        createTransactionUiState = createTransactionUiState.copy(
            categoryTypeError = categoryTypeResult.errorMessage)
        return categoryTypeResult.successful
    }

    private fun validateTransactionType(): Boolean {
        val transactionTypeResult = transactionTypeValidator.execute(
            input = createTransactionUiState.transactionType)
        createTransactionUiState = createTransactionUiState.copy(
            transactionTypeError = transactionTypeResult.errorMessage)
        return transactionTypeResult.successful
    }

    private fun validatePaymentAccountId(): Boolean {
        val paymentAccountIdResult = validatePaymentAccountIdUseCase.execute(
            input = createTransactionUiState.paymentAccountId)
        createTransactionUiState = createTransactionUiState.copy(
            paymentAccountIdError = paymentAccountIdResult.errorMessage)
        return paymentAccountIdResult.successful
    }

    private fun saveTransaction() {

    }
}

