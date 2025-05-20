package com.learn.quest2.presentation.composable.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.learn.quest2.helper.Screens
import com.learn.quest2.presentation.viewmodel.ECommerceViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ECommerceScreen(
    navController: NavController,
    viewModel: ECommerceViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Text(
                text = "Order Screen",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(4.dp),
                onClick = {
                    navController.navigate(Screens.STATS.name)
                }) {
                Text(text = "More Stats")
            }
            Text(
                text = "Total Revenue: $${uiState.totalRevenue}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.End,
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(color = Color.White)
        ) {
            item {
                uiState.orderList.forEach { order ->
                    var isExpanded by remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Order Id: ${order.orderId}", maxLines = 1, overflow =
                                TextOverflow.Ellipsis
                            )
                            Text(
                                text = "User Id: ${order.userId}", maxLines = 1, overflow =
                                TextOverflow.Ellipsis
                            )
                            Text(text = "Date: ${order.timestamp.format(DateTimeFormatter.ISO_DATE)}")
                            Text(text = "Total order price: $${order.totalOrderValue}")
                            Text(
                                text = if (isExpanded) "Hide Order Details -" else "Show Order Details +",
                                textDecoration = TextDecoration.Underline,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .clickable { isExpanded = !isExpanded }
                            )

                            AnimatedVisibility(visible = isExpanded) {
                                Column {
                                    order.items.forEach { item ->
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Column(
                                            modifier = Modifier
                                                .border(
                                                    width = 1.dp,
                                                    color = Color.Gray,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .fillMaxWidth()
                                                .padding(horizontal = 24.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = "Product Id: ${item.productId}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(text = "Price: $${item.price}/item")
                                            Text(text = "Quantity: ${item.quantity}")
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}