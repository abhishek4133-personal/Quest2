package com.learn.quest2.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.learn.quest2.R

@Composable
fun Header(
    modifier: Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    Row(modifier = Modifier) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Search") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        IconButton(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically), onClick = onFilterClick
        ) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_filter_icon), contentDescription = "filter", tint = Color.Unspecified)
        }
    }
}