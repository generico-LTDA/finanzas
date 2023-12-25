package com.soleel.home.modals

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soleel.ui.R
import com.soleel.home.HomeUiState
import com.soleel.home.HomeViewModel
//import com.soleel.home.R

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    MonetaTheme {
//        AddCards()
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddModalBottomSheet(
    homeViewModel: HomeViewModel,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        AddCards(homeViewModel)
    }
}

@Composable
private fun AddCards(homeViewModel: HomeViewModel) {

    val paymentAccountsUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

//    val paymentAccountsExistEntity: Boolean = paymentAccountsUiState.isPaymentAccountSuccess

//    ) {
//        is HomeUiState.Loading -> false
//        is HomeUiState.Success<*> -> (paymentAccountsUiState as HomeUiState.Success<List<PaymentAccountEntity>>).data.isNotEmpty()
//        is HomeUiState.Error<*> -> false
//    }

    Row(
        modifier = Modifier.navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AddCardItem(
            modifier = Modifier.weight(1f),
            iconResId = R.drawable.ic_add_account,
            name = "Crear cuenta"
        )
        AddCardItem(
            modifier = Modifier.weight(1f),
            iconResId = R.drawable.ic_add_transaction,
            name = "Crear transacción",
            paymentAccountExist = paymentAccountsUiState.isPaymentAccountSuccess
        )
    }
}

@Composable
private fun AddCardItem(
    modifier: Modifier,
    iconResId: Int,
    name: String,
    paymentAccountExist: Boolean = true
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(enabled = paymentAccountExist) {
                onClick(context, name)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Icono de agregar",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = name,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

private fun onClick(context: Context, name: String) {
    when (name) {
        "Crear cuenta" -> {
//            context.startActivity(Intent(context, CreatePaymentAccountActivity::class.java))
        }

        "Crear transacción" -> {
//            context.startActivity(Intent(context, CreateTransactionActivity::class.java))
        }
    }
}