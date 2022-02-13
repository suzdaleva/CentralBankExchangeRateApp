package com.manicpixie.cfttest.presentation.currencies_list

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.domain.util.CurrenciesOrder
import com.manicpixie.cfttest.presentation.currencies_list.components.*
import com.manicpixie.cfttest.presentation.main.drawColoredShadow
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.backgroundGradient
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@InternalCoroutinesApi
@SuppressLint("SimpleDateFormat")
@ExperimentalAnimationApi
@Composable
fun CurrenciesListScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrenciesListViewModel = hiltViewModel()
) {
    val configuration =  LocalConfiguration.current.orientation
    val queryState = viewModel.searchQuery.value
    val contentState = viewModel.contentState.value
    var backgroundExpanded by rememberSaveable { mutableStateOf(false) }
    val currentCurrenciesOrder = remember {
        mutableStateOf(contentState.currenciesOrder)
    }

    val listState = rememberLazyListState()

    val sortedListState = remember {
        mutableStateOf(contentState.currencies)
    }

    var scrolledY = 0f
    var previousOffset = 0
    val backScroll = remember {
        mutableStateOf(0f)
    }


    AnimatedVisibility(
        visible = backgroundExpanded,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 500),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(animationSpec = tween(durationMillis = 500))
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    if(configuration != Configuration.ORIENTATION_LANDSCAPE)
                    translationY -= backScroll.value
                }
                .drawColoredShadow(
                    alpha = 0.5f,
                    shadowRadius = 20.dp,
                    color = Color(0xFF6100FF),
                    shape = "rectangle",
                    borderRadius = 63.dp
                )
                .fillMaxWidth()
                .height(540.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 0.dp, 63.dp))
                .background(
                    brush = backgroundGradient
                )
        )
    }
    AnimatedVisibility(
        visible = !backgroundExpanded,

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = if (listState.firstVisibleItemIndex == 0) {
                        1 - (listState.firstVisibleItemScrollOffset / 200f)
                    } else 0f
                }
                .drawColoredShadow(
                    alpha = 0.5f,
                    shadowRadius = 20.dp,
                    color = Color(0xFF6100FF),
                    shape = "rectangle",
                    borderRadius = 63.dp
                )
                .height(296.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 63.dp, 63.dp))
                .background(
                    brush = backgroundGradient
                )
        ) {}
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                bottom = 66.dp
            )
    ) {

        LazyColumn(
            state = listState
        ) {

            item {
                Header(
                    modifier = Modifier.graphicsLayer {

                        scrolledY += listState.firstVisibleItemScrollOffset - previousOffset
                        backScroll.value = scrolledY
                        if(contentState.currencies.isNotEmpty()) {
                            backScroll.value = scrolledY / 0.5f
                            translationY = scrolledY / 0.5f
                            alpha = 1 - (scrolledY / 600f)
                        }
                        previousOffset = listState.firstVisibleItemScrollOffset
                    },
                    text = queryState.query,
                    hint = queryState.hint,
                    isHintVisible = queryState.isHintVisible,
                    onValueChange = {
                        viewModel.onEvent(
                            CurrenciesListEvent.EnteredQuery(
                                it,
                                currentCurrenciesOrder.value
                            )
                        )
                    },
                    onFocusChange = {
                        viewModel.onEvent(CurrenciesListEvent.ChangeQueryFocus(it))
                    })

            }
            if (contentState.isLoading && queryState.query.isBlank() && !backgroundExpanded) {
                items(count = 6) { item ->
                    ShimmerAnimation()
                }
            }
            if (contentState.currencies.isNotEmpty()) {
                backgroundExpanded = false
                sortedListState.value = contentState.currencies
                items(contentState.currencies)
                { currency ->
                    CurrencyItem(currency = currency)
                }
            }
            if (!contentState.isLoading
                && (sortedListState.value.isEmpty() || contentState.currencies.isEmpty()) && queryState.query.isNotBlank()
            ) {

                item {
                    EmptySearchScreen(visible = backgroundExpanded)
                }
                backgroundExpanded = true
            }
            if (contentState.error.isNotBlank() && queryState.query.isBlank()) {
                item {
                    ErrorScreen(
                        visible = backgroundExpanded,
                        onClick = {
                            viewModel.searchCurrencies(
                                queryState.query,
                                currentCurrenciesOrder.value
                            )
                        })
                    backgroundExpanded = true
                }
            }
        }

    }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9501FF))
                .padding(top = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = SimpleDateFormat("d.MM.yy").format(Date()),
                style = MaterialTheme.typography.h1,
                fontSize = dpToSp(dp = 14.dp)
            )
            IconButton(
                enabled = !backgroundExpanded,
                modifier = Modifier.padding(end = 10.dp), content = {
                    Icon(
                        painter = if (currentCurrenciesOrder.value == CurrenciesOrder.Initial) painterResource(
                            id = R.drawable.initial_sort_icon
                        ) else painterResource(id = R.drawable.byvalue_sort_icon),
                        contentDescription = "Sort", tint = Color.White
                    )
                },
                onClick = {
                    currentCurrenciesOrder.value =
                        if (currentCurrenciesOrder.value == CurrenciesOrder.Initial) CurrenciesOrder.ByValue else CurrenciesOrder.Initial
                    viewModel.onEvent(CurrenciesListEvent.Order(currentCurrenciesOrder.value))
                })
        }
    }