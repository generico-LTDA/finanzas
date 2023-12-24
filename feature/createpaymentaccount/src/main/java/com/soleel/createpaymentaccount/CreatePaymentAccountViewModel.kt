package com.soleel.createpaymentaccount

import android.util.Log
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


data class AddPaymentAccountUiState(
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

    private val _addPaymentAccountUiState: MutableStateFlow<AddPaymentAccountUiState> =
        MutableStateFlow(AddPaymentAccountUiState())
    val addPaymentAccountUiState: StateFlow<AddPaymentAccountUiState> =
        _addPaymentAccountUiState.asStateFlow()

    fun savePaymentAccount() {

        if (_addPaymentAccountUiState.value.accountType == null) {
            Log.d("Moneta", "Debe seleccionarse un tipo de cuenta")
            _addPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Debe seleccionarse un tipo de cuenta")
                })
            return
        }

        if (_addPaymentAccountUiState.value.name.isEmpty()) {
            Log.d("Moneta", "Nombre no puede estar vacia")
            _addPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Nombre no puede estar vacia")
                })
            return
        }

        if (_addPaymentAccountUiState.value.initialAmount == null
            || _addPaymentAccountUiState.value.initialAmount !in 1..9999999
        ) {
            Log.d("Moneta", "Valor no puede ser inferior a 0 o mayor que 9999999")
            _addPaymentAccountUiState.update(
                function = {
                    it.copy(userMessage = "Valor no puede ser inferior a 0 o mayor que 9999999")
                })
            return
        }

        this.createPaymentAccount()
    }

    private fun createPaymentAccount() {
        Log.d("Moneta", "createPaymentAccount")

//        _addPaymentAccountUiState.update(
//            function = {
//                it.copy(createAt = System.currentTimeMillis())
//            })

        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                paymentAccountRepository.createPaymentAccount(
                    name = _addPaymentAccountUiState.value.name,
                    initialAmount = _addPaymentAccountUiState.value.initialAmount ?: 0,
                    accountType = _addPaymentAccountUiState.value.accountType ?: 0
                )

                _addPaymentAccountUiState.update(
                    function = {
                        it.copy(isPaymentAccountSaved = true)
                    }
                )
            })
    }

    fun userMessageShown() {
        _addPaymentAccountUiState.update(
            function = {
                it.copy(userMessage = null)
            })
    }

    fun updateTypeAccount(newAccountType: Int) {
        _addPaymentAccountUiState.update(
            function = {
                it.copy(accountType = newAccountType)
            })
    }

    fun updateName(newName: String) {

        if (30 <= newName.length) {
            return
        }

        _addPaymentAccountUiState.update(
            function = {
                it.copy(name = newName)
            })
    }

    fun updateInitialAmount(newInitialAmount: String) {
        // README: Esto tambien soluciona el intento de ingresar letras.
        // Si se intenta ingresar letras desde un teclado fisico, simplementa no cambiara el valor
        // en el estado del UI
        val intValue = newInitialAmount.toIntOrNull()
        _addPaymentAccountUiState.update(
            function = {
                it.copy(initialAmount = intValue)
            })
    }

}
