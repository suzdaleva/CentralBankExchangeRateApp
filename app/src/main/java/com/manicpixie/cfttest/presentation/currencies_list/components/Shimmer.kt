package com.manicpixie.cfttest.presentation.currencies_list.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp





@Composable
fun ShimmerItem(
    brush: Brush,
) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(114.dp)
        .padding(horizontal = 20.dp, vertical = 5.dp),
        elevation = 6.dp) {
        Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(15.dp))
            Box(modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(brush = brush)){}
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .width(130.dp).height(16.dp)
                        .clip(RoundedCornerShape(8.dp)).background(brush = brush)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Spacer(
                    modifier = Modifier
                        .width(80.dp).height(12.dp)
                        .clip(RoundedCornerShape(8.dp)).background(brush = brush)
                )
            }

        }

    }

}



@Composable
fun ShimmerAnimation(
) {

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(

        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(



            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colors.onSecondary,
            MaterialTheme.colors.onPrimary,
            MaterialTheme.colors.onSecondary
        ),
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}