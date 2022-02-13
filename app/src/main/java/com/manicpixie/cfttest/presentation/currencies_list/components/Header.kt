package com.manicpixie.cfttest.presentation.currencies_list.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.presentation.utils.dpToSp


@ExperimentalAnimationApi
@Composable
fun Header(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    isHintVisible: Boolean,
    text: String,
    hint: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 7.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(id = R.string.exchange_rate),
            style = MaterialTheme.typography.caption,
            fontSize = dpToSp(dp = 26.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.central_bank),
            style = MaterialTheme.typography.body1.copy(
                textAlign = TextAlign.Center,
                fontSize = dpToSp(dp = 14.dp)
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        SearchBar(
            textStyle = MaterialTheme.typography.body1.copy(
                fontSize = dpToSp(dp = 14.dp)
            ),
            text = text,
            hint = hint,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .size(20.dp),
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Sort",
                            tint = Color.White
                        )
                    },
                    onClick = { })
            },
            onValueChange = onValueChange,
            onFocusChange = onFocusChange,
            isHintVisible = isHintVisible,
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}