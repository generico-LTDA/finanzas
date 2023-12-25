package com.soleel.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soleel.transaction.model.Transaction
import com.soleel.home.HomeUiState
import com.soleel.home.HomeViewModel

//@Preview
//@Composable
//private fun Preview() {
//
//    val context = LocalContext.current
//    Repository.provide(context)
//    val localRepository = Repository.localRepository
//
//    val homeViewModel: HomeViewModel = HomeViewModel(localRepository)
//
//    homeViewModel.fetchPaymentAccounts()
//
//    MonetaTheme {
//        HomeScreen(homeViewModel)
//    }
//}

@Composable
fun InitScreen(
    homeViewModel: HomeViewModel
) {
    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        content = {

            if (homeUiState.isPaymentAccountLoading && homeUiState.isTransactionLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }

            if (homeUiState.isPaymentAccountSuccess && homeUiState.isTransactionSuccess) {
                val transactions: List<Transaction> = homeUiState.itemsTransaction

                if (transactions.isEmpty()) {
                    Text(
                        text = "No existen transacciones actualmente.",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                } else {
                    LazyColumn {
                        items(transactions) { transaction ->
                            TransactionItem(transaction)
                        }
                    }
                }
            }


//            when (homeUiState) {
//                is HomeUiState.Loading -> {
//                    // Muestra una pantalla de carga
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .fillMaxSize()
//                            .wrapContentSize(Alignment.Center)
//                    )
//                }
//
//                is HomeUiState.Success<*> -> {
//                    // Muestra los datos exitosos
//                    val transactions: List<TransactionEntity> =
//                        (homeUiState as HomeUiState.Success<List<TransactionEntity>>).data
//
//                    if (transactions.isEmpty()) {
//                        Text(
//                            text = "No existen transacciones actualmente.",
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black,
//                            modifier = Modifier.align(Alignment.CenterHorizontally),
//                            textAlign = TextAlign.Center,
//                            fontSize = 16.sp
//                        )
//                    } else {
//                        LazyColumn {
//                            items(transactions) { transaction ->
//                                TransactionItem(transaction)
//                            }
//                        }
//                    }
//                }

//                is HomeUiState.Error<*> -> {
//                    // Muestra un mensaje de error en caso de falla
//                    val failedState: HomeUiState.Error<Exception> = homeUiState as HomeUiState.Error<Exception>
//                    val errorMessage = failedState.error.message ?: "Error desconocido"
//
//                    Text(
//                        text = "Error: $errorMessage",
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Red,
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                        textAlign = TextAlign.Center,
//                        fontSize = 16.sp
//                    )
//                }

        }
    )
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Text(text = transaction.name)
}