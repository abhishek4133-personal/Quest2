package com.learn.quest2.presentation.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.learn.quest2.R
import com.learn.quest2.presentation.viewmodel.ECommerceViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: ECommerceViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllStatistics()
    }

    Scaffold(
        topBar = {
            Box {
                IconButton(
                  onClick = navController::navigateUp
                ) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        contentDescription = "filter", tint = Color.Unspecified)
                }
                Text(
                    text = "Statistics",
                    modifier = Modifier
                        .padding(vertical = 12.dp)
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
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card {
                Label(
                    labelText = "Most Expensive Product Item"
                )
                StatsItem(
                    text = "Product ID =====> ${uiState.expensiveOrder?.productId}"
                )
                StatsItem(
                    text = "Price =====> $${uiState.expensiveOrder?.price}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Card {
                Label(
                    labelText = "Unique Product Item IDs"
                )
                uiState.uniqueProductIds?.forEach() { ids ->
                    StatsItem(
                        text = "ids =====> $ids"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Card {
                Label(
                    labelText = "Product Sale Count"
                )
                uiState.productSaleCount?.forEach { map ->
                    StatsItem(
                        text = "${map.key} =====> ${map.value} times"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Card {
                Label(
                    labelText = "Spending For Users"
                )
                uiState.spendingForEachUser?.forEach { map ->
                    StatsItem(
                        text = "${map.key} =====> $${map.value}"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Card {
                Label(
                    labelText = "Loyalty Points For ${uiState.userList.first().username}"
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
    labelText: String
) {
    Text(
        text = labelText,
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
    Column {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
