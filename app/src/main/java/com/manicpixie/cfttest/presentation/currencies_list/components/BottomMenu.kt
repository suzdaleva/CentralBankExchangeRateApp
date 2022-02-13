package com.manicpixie.cfttest.presentation.currencies_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manicpixie.cfttest.ui.theme.Blue
import com.manicpixie.cfttest.ui.theme.DarkestNavy

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeTextColor: Color = Blue,
    inactiveTextColor: Color = MaterialTheme.colors.onSurface,
    initialSelectedItemIndex: Int = 0,
    onSelect: (Int) -> Unit

) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(MaterialTheme.colors.surface)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                inactiveTextColor = inactiveTextColor,
                activeTextColor = activeTextColor,
                onItemClick = {
                    selectedItemIndex = index
                    onSelect(selectedItemIndex)}
            )
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeTextColor: Color = DarkestNavy,
    inactiveTextColor: Color = Blue,
    onItemClick: () -> Unit
) {
            IconButton(onClick = onItemClick, content = {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(25.dp)
            )
        })
    }