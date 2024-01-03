package com.soleel.createtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.common.result.Result
import com.soleel.common.result.asResult
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.transaction.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


//data class CreateTransactionUiState(
//    val name: String? = null,
//    val amount: Int? = null,
//    val description: String? = null,
//    val categoryType: Int? = null,
//    val transactionType: Int? = null,
//    val paymentAccountId: Int? = null,
//
//    val userMessage: String? = null,
//    val isTransactionSaved: Boolean = false
//)

sealed interface CreateTransactionUiState {
    data class Success(val transaction: Transaction) : CreateTransactionUiState
    data object Error : CreateTransactionUiState
    data object Loading : CreateTransactionUiState
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

    private val _createTransactionUiState = MutableStateFlow<CreateTransactionUiState>(CreateTransactionUiState.Loading)
    val createTransactionUiState: StateFlow<CreateTransactionUiState> = _createTransactionUiState.asStateFlow()

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

}

