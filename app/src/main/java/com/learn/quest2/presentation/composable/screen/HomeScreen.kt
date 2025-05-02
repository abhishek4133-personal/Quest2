package com.learn.quest2.presentation.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.learn.quest2.R
import com.learn.quest2.data.model.Product
import com.learn.quest2.presentation.composable.FilterBottomSheetContent
import com.learn.quest2.presentation.composable.Header
import com.learn.quest2.presentation.viewmodel.ProductListingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<ProductListingViewModel>()
    val uiState by viewModel.state.collectAsState()
    val filterState by viewModel.filterTypeState.collectAsState()

    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        topBar = {
            Header(
                searchQuery = filterState.searchTerm,
                onSearchQueryChange = { viewModel.onSearch(searchTerm = it) },
                onFilterClick = { isBottomSheetVisible = true }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
            ) {
                items(uiState.products.size) { index ->
                    val product = uiState.products[index]
                    ProductItem(product = product)
                }
            }
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false }
        ) {
            FilterBottomSheetContent(
                viewModel = viewModel,
                onDismiss = { isBottomSheetVisible = false },
                onRefresh = {
                    viewModel.onManualRefresh()
                }
            )
        }
    }
}

@Composable
private fun ProductItem(product: Product) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row {
            val imageUrl = product.images?.firstOrNull()
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .padding(8.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                    .crossfade(true).transformations(CircleCropTransformation())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .build(),
                contentDescription = "product image", alignment = Alignment.Center,
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.title ?: "No Title",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = product.description ?: "No Description",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = if (product.price != null) "$" + product.price else "Price Unavailable",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
