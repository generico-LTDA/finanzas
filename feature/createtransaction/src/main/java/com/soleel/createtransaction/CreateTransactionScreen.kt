package com.soleel.createtransaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
){
    CreateTransactionScreen(modifier = modifier, onBackClick = onBackClick)
}

@Composable
private fun CreateTransactionScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Create Transaction Activity",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}