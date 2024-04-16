package com.soleel.add

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMultiFloatingActionButton(
    onCreatePaymentAccount: () -> Unit,
    onCreateTransaction: () -> Unit,
    onHideBottomBar: () -> Unit,
    onHideAddFloating: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    viewModel: AddModalViewModel = hiltViewModel(),
) {




}