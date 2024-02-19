package com.soleel.ui.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class PaymentAccountCardItem(
    val type: Int,
    val typeName: String,
    var typeNameAccount: String,
    var amount: String = "$1,000,000",
    val letterColor: Color,
    val gradientBrush: Brush,
    val icon: Int
)

@Composable
fun PaymentAccountCard(
    paymentAccountCardItem: PaymentAccountCardItem,
    onClick: () -> Unit = {},
    onClickEnable: Boolean = true
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(enabled = onClickEnable, onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            Column(
                modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = paymentAccountCardItem.typeName,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = paymentAccountCardItem.amount,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .padding(16.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = paymentAccountCardItem.icon),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = paymentAccountCardItem.letterColor
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                content = {
                                    Text(
                                        modifier = Modifier,
                                        text = "1234 5678 9012 3456",
                                        color = paymentAccountCardItem.letterColor,
                                        style = MaterialTheme.typography.headlineLarge,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        modifier = Modifier,
                                        text = paymentAccountCardItem.typeNameAccount,
                                        color = paymentAccountCardItem.letterColor,
                                        style = MaterialTheme.typography.titleLarge,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}