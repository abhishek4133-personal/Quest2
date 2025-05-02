package com.learn.quest2.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.learn.quest2.presentation.state.Category
import com.learn.quest2.presentation.state.FilterEvent
import com.learn.quest2.presentation.state.PriceRange
import com.learn.quest2.presentation.state.SortOrder
import com.learn.quest2.presentation.viewmodel.ProductListingViewModel

@Composable
fun FilterBottomSheetContent(
    viewModel: ProductListingViewModel, onDismiss: () -> Unit, onRefresh: () -> Unit
) {
    val state by viewModel.filterTypeState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {

        Button(onClick = onRefresh) {
            Text(text = "Refresh", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose a Sort Order",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
        SortOrder.entries.forEach { option ->

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onQueryChanged(
                        FilterEvent.OnSortingOrderChanged(
                            sortOrder = option,
                        )
                    )
                }) {

                RadioButton(selected = (option == state.sortOrder), onClick = {
                    viewModel.onQueryChanged(
                        FilterEvent.OnSortingOrderChanged(
                            sortOrder = option,
                        )
                    )
                })

                Text(
                    text = option.name, modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose a Category",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
        Category.entries.forEach { option ->

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onQueryChanged(
                        FilterEvent.OnCategoryChanged(
                            category = option,
                        )
                    )
                }) {

                RadioButton(selected = (option == state.category), onClick = {
                    viewModel.onQueryChanged(
                        FilterEvent.OnCategoryChanged(
                            category = option,
                        )
                    )
                })

                Text(
                    text = option.name, modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose a Price Range",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )

        PriceRange.entries.forEach { option ->

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onQueryChanged(
                        FilterEvent.OnPriceRangeChanged(
                            priceRange = option,
                        )
                    )
                }) {

                RadioButton(selected = (option == state.priceRange), onClick = {
                    viewModel.onQueryChanged(
                        FilterEvent.OnPriceRangeChanged(
                            priceRange = option,
                        )
                    )
                })

                Text(
                    text = when {
                        (option == PriceRange.LESS_THAN_1000) -> "Lesser than 1000"
                        (option == PriceRange.LESS_THAN_100) -> "Lesser than 100"
                        (option == PriceRange.LESS_THAN_50) -> "Lesser than 50"
                        (option == PriceRange.LESS_THAN_10) -> "Lesser than 10"
                        (option == PriceRange.LESS_THAN_5) -> "Lesser than 5"
                        (option == PriceRange.ALL) -> "All Products"
                        else -> "All Products"
                    }, modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}