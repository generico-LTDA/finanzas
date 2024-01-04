package com.soleel.common.createuistate


fun <T: CreateUiState> updateUserMessage(
    uiState: T,
    userMessage: String?
): T {
    return when (uiState) {
        is CreateUiState.Loading -> (uiState as CreateUiState.Loading).copy(userMessage = userMessage) as T
        else -> uiState
    }
}
