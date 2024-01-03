package com.soleel.createpaymentaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreatePaymentAccountUiState(
    val name: String = "",
    val initialAmount: Int? = null,
    val accountType: Int? = null,

    val userMessage: String? = null,
    val isPaymentAccountSaved: Boolean = false
)

@HiltViewModel
class CreatePaymentAccountViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource
) : ViewModel() {

    private val _createPaymentAccountUiState: MutableStateFlow<CreatePaymentAccountUiState> =
        MutableStateFlow(CreatePaymentAccountUiState())
    val createPaymentAccountUiState: StateFlow<CreatePaymentAccountUiState> =
        _createPaymentAccountUiState.asStateFlow()

    fun savePaymentAccount() {
        if (_createPaymentAccountUiState.value.accountType == null) {
            _createPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Debe seleccionarse un tipo de cuenta")
                })
            return
        }

        if (_createPaymentAccountUiState.value.name.isEmpty()) {
            _createPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Nombre no puede estar vacia")
                })
            return
        }

        if (_createPaymentAccountUiState.value.initialAmount == null
            || _createPaymentAccountUiState.value.initialAmount !in 1..9999999
        ) {
            _createPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Valor no puede ser inferior a 0 o mayor que 9999999")
                })
            return
        }

        this.createPaymentAccount()
    }

    private fun createPaymentAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                paymentAccountRepository.createPaymentAccount(
                    name = _createPaymentAccountUiState.value.name,
                    initialAmount = _createPaymentAccountUiState.value.initialAmount ?: 0,
                    accountType = _createPaymentAccountUiState.value.accountType ?: 0
                )

                _createPaymentAccountUiState.update(
                    function = {
                        it.copy(isPaymentAccountSaved = true)
                    }
                )
            })
    }

    fun userMessageShown() {
        _createPaymentAccountUiState.update(
            function = {
                it.copy(userMessage = null)
            })
    }

    fun updateTypeAccount(newAccountType: Int) {
        _createPaymentAccountUiState.update(
            function = {
                it.copy(accountType = newAccountType)
            })
    }

    fun updateName(newName: String) {

        if (30 <= newName.length) {
            return
        }

        _createPaymentAccountUiState.update(
            function = {
                it.copy(name = newName)
            })
    }

    fun updateInitialAmount(newInitialAmount: String) {
        // README: Esto tambien soluciona el intento de ingresar letras.
        // Si se intenta ingresar letras desde un teclado fisico, simplementa no cambiara el valor
        // en el estado del UI
        val intValue = newInitialAmount.toIntOrNull()
        _createPaymentAccountUiState.update(
            function = {
                it.copy(initialAmount = intValue)
            })
    }

}
