package com.soleel.cancelalert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun CancelAlertDialog(
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onShowBottomBar()
                    onShowAddFloating()
                    onConfirmation()
                    onDismissRequest()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        }
    )
}
