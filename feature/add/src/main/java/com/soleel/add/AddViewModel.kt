package com.soleel.add

import androidx.lifecycle.ViewModel
import com.soleel.paymentaccount.model.PaymentAccount
import com.soleel.transaction.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AddUiState(
    val itemsTransaction: List<Transaction> = emptyList(),
    val itemsPaymentAccount: List<PaymentAccount> = emptyList(),

    val userMessage: String? = null,
    val isPaymentAccountLoading: Boolean = false,
    val isPaymentAccountSuccess: Boolean = false,
    val isTransactionLoading: Boolean = false,
    val isTransactionSuccess: Boolean = false
)

@HiltViewModel
class AddViewModel @Inject constructor(): ViewModel() {


    private val _addUiState: MutableStateFlow<AddUiState> =
        MutableStateFlow(AddUiState())

    val addUiState: StateFlow<AddUiState> =
        _addUiState.asStateFlow()

}