package com.soleel.common.createuistate


interface CreateUiState {
    data class Loading(val userMessage: String? = null) : CreateUiState
}