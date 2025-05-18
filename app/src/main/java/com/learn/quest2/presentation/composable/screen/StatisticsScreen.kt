package com.learn.quest2.presentation.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learn.quest2.presentation.viewmodel.ECommerceViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: ECommerceViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Row {
                Text(
                    text = "Statistic",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .verticalScroll(rememberScrollState())
        ) {

            Card {
                Label(
                    text = "Most Expensive Product Item"
                )
                StatsItem(
                    text = "Product ID: ${uiState.expensiveOrder?.productId}"
                )
                StatsItem(
                    text = "Price: $${uiState.expensiveOrder?.price}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card {
                Label(
                    text = "Unique Product Item IDs"
                )
                uiState.uniqueProductIds?.forEach() { ids ->
                    StatsItem(
                        text = "ids -> $ids"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card {
                Label(
                    text = "Product Sale Count"
                )
                uiState.productSaleCount?.forEach { map ->
                    StatsItem(
                        text = "${map.key} -> ${map.value} times"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card {
                Label(
                    text = "Spending For Users"
                )
                uiState.spendingForEachUser?.forEach { map ->
                    StatsItem(
                        text = "${map.key} -> $${map.value}"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card {
                Label(
                    text = "Loyalty Points For First User"
                )
                StatsItem(
                    text = "${uiState.loyaltyPoints}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun Label(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color.Black
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun StatsItem(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color.Black
        ),
    )
}
