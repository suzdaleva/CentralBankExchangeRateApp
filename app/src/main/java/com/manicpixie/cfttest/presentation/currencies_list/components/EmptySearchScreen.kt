package com.manicpixie.cfttest.presentation.currencies_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.montserratFont
import com.manicpixie.cfttest.ui.theme.robotoFont


@ExperimentalAnimationApi
@Composable
fun EmptySearchScreen(
    visible: Boolean = false
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 400),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(animationSpec = tween(durationMillis = 400))
    ) {
        Column(
            modifier = Modifier
                //.verticalScroll(state = ScrollState(0))
                .fillMaxSize()
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 50.dp),
                painter = painterResource(id = R.drawable.megaphone),
                contentDescription = "Error"
            )
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.empty_search_message),
                style = TextStyle(
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = dpToSp(dp = 24.dp),
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.try_another_request),
                style = TextStyle(
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = dpToSp(dp = 13.dp),
                    letterSpacing = 0.03.em
                ),
            )
        }
    }
}