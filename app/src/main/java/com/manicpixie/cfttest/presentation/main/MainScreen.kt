package com.manicpixie.cfttest.presentation.main


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.presentation.converter.ConverterEvent
import com.manicpixie.cfttest.presentation.converter.ConverterScreen
import com.manicpixie.cfttest.presentation.converter.ConverterViewModel
import com.manicpixie.cfttest.presentation.currencies_list.CurrenciesListScreen
import com.manicpixie.cfttest.presentation.currencies_list.CurrenciesListViewModel
import com.manicpixie.cfttest.presentation.currencies_list.components.*
import com.manicpixie.cfttest.ui.theme.Blue
import com.manicpixie.cfttest.ui.theme.fabGradient
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    currenciesListViewModel: CurrenciesListViewModel = hiltViewModel(),
    converterViewModel: ConverterViewModel = hiltViewModel()
){

    val scaffoldState = rememberScaffoldState()
    val queryState = currenciesListViewModel.searchQuery.value

    val contentState = currenciesListViewModel.contentState.value

    val currentScreenIndex = rememberSaveable {
        mutableStateOf(0)
    }

    var backgroundExpanded by rememberSaveable { mutableStateOf(false) }


    Scaffold(isFloatingActionButtonDocked = true, scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = { if(currentScreenIndex.value == 0) {
                if(currenciesListViewModel.contentState.value.currencies.isNotEmpty()) {
                    currenciesListViewModel.getCurrencies(contentState.currenciesOrder)
                } else
                currenciesListViewModel.searchCurrencies(queryState.query, contentState.currenciesOrder)}

                 else if(converterViewModel.inputValue.value.query.isNotBlank()) converterViewModel.onEvent(ConverterEvent.Calculate)
                      },
            shape = CircleShape,
            modifier = Modifier
                .drawColoredShadow(color = Blue, shape = "circle")
                .size(60.dp)
        ) { Box(modifier = Modifier
            .clip(CircleShape)
            .size(60.dp)
            .background(brush = fabGradient), contentAlignment = Alignment.Center) {
            Icon(painter = if(currentScreenIndex.value == 0)
            painterResource(id = R.drawable.refresh_icon) else painterResource(id = R.drawable.equals_icon),
                contentDescription = "FAB", tint = Color.White)}
        }
    }, floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomMenu(
            items = listOf(
                BottomMenuContent("Валюты", R.drawable.menu_currencies_icon),
                BottomMenuContent("Конвертер", R.drawable.menu_converter_icon),
            ), onSelect = {
                    currentScreenIndex.value = it}

        ) }) {
        if(currentScreenIndex.value == 0) {
            backgroundExpanded = false
            CurrenciesListScreen()

        }
        else{
            converterViewModel.filterCurrencies(converterViewModel.currentCurrency.value.name)
            ConverterScreen(expandBackground = backgroundExpanded, onClick = {currentScreenIndex.value = 0})
            backgroundExpanded = true
        }

    }
}


fun Modifier.drawColoredShadow(
    shape: String,
    color: Color,
    alpha: Float = 0.8f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 10.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )

        when(shape) {
            "circle" -> it.drawCircle(center = Offset(this.size.width/2, this.size.height/2), radius = this.size.width/2, paint = paint)
            "rectangle" -> it.drawRoundRect( 0f, 0f, this.size.width, this.size.height, borderRadius.toPx(), borderRadius.toPx(), paint
        )
        }
    }
}