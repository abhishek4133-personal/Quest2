package com.learn.quest2.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.learn.quest2.R

@Composable
fun Header(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    Row {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Search") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Handle search action if needed
                }
            )
        )

        IconButton(
            modifier = Modifier.wrapContentSize(), onClick = onFilterClick
        ) {
            Icon(painter = painterResource(R.drawable.ic_filter), contentDescription = "filter")
        }
    }
}