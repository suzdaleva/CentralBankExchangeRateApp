package com.manicpixie.cfttest.presentation.converter

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.common.Constants
import com.manicpixie.cfttest.presentation.converter.components.InputField
import com.manicpixie.cfttest.presentation.main.drawColoredShadow
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.backgroundGradient
import com.manicpixie.cfttest.ui.theme.montserratFont
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@ExperimentalAnimationApi
@Composable
fun ConverterScreen(
    modifier: Modifier = Modifier,
    onClick:()-> Unit,
    viewModel: ConverterViewModel = hiltViewModel(),
    expandBackground: Boolean = false
) {


    var expanded by remember { mutableStateOf(false) }

    val suggestions : List<String> =  Constants.currenciesNames.values.map { it[0] }

    var selectedText  = viewModel.currentCurrency.value.name

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val result = viewModel.currentResult.value

    val icon = painterResource(id = R.drawable.down_arrow)

    val currentCurrencyState = viewModel.currentCurrency.value

    val inputValueState = viewModel.inputValue.value
    
    val isDatabaseEmpty = viewModel.isDatabaseEmpty.value

    Box(
        modifier = Modifier
            .verticalScroll(state = ScrollState(0))
            .fillMaxSize()
            .padding(bottom = 66.dp)
    ) {
        AnimatedVisibility(
            visible = expandBackground && !isDatabaseEmpty,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 400),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 400))
        ) {
            Box(
                modifier = Modifier
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
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.converter),
                        style = MaterialTheme.typography.caption,
                        fontSize = dpToSp(dp = 26.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        text = stringResource(id = R.string.input_sum),
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = dpToSp(dp = 16.dp),
                            textAlign = TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    InputField(
                        hint = inputValueState.hint,
                        text = inputValueState.query,
                        isHintVisible = inputValueState.isHintVisible,
                        onFocusChange = {
                            viewModel.onEvent(ConverterEvent.ChangeTextFocus(it))
                        }, onValueChange = {
                            viewModel.onEvent(ConverterEvent.EnteredText(it))
                        }, currencyName = setTextForRoubles(inputValueState.query)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(Modifier.padding(horizontal = 20.dp)) {
                        Row(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .height(40.dp)
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                }
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(modifier = Modifier.weight(1f)) {
                                Text(
                                    style = TextStyle(
                                        fontFamily = montserratFont,
                                        fontSize = dpToSp(dp = 16.dp),
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    ),
                                    text = selectedText,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()

                                )

                            }
                            IconButton(
                                content = {
                                    Icon(painter = icon, contentDescription = "Down", tint = Color.White)
                                },
                                onClick = {expanded = !expanded})
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(Color.White)
                                .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
                        ) {
                            suggestions.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    selectedText = label
                                    expanded = false
                                    viewModel.onEvent(ConverterEvent.Filter(selectedText))
                                }) {
                                    Text(text = label,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            fontFamily = montserratFont,
                                            fontSize = dpToSp(dp = 16.dp),
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black
                                        ))
                                }
                            }
                        }
                    }
                }

            }

            InputField(
                modifier = Modifier.padding(top = 315.dp),
                text = if (result == 0.0) "0.0" else "%.3f".format(result).replace(',', '.'),
                isHintVisible = false,
                enabled = false,
                onFocusChange = {}, onValueChange = {}, currencyName = Constants.currenciesNames[currentCurrencyState.charCode]!![2])
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 432.dp)
            ) {

                Column(modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.TopStart),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "1 ${currentCurrencyState.charCode} = " + "%.3f".format(currentCurrencyState.value).replace(',', '.') + " RUB",
                        style = TextStyle(
                            fontFamily = montserratFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = dpToSp(dp = 14.dp),
                            letterSpacing = 0.04.em,
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.graph),
                        contentDescription = "Graph",
                        modifier = Modifier.padding(top = 13.dp)
                    )}
                Image(
                    painter = painterResource(
                        id = R.drawable.hand_with_money
                    ), contentDescription = null,
                    modifier = Modifier
                        .size(270.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .verticalScroll(state = ScrollState(0))
            .fillMaxSize()
            .padding(bottom = 66.dp)
    ) {
        AnimatedVisibility(
            visible = expandBackground && isDatabaseEmpty,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 400),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 400))
        ) {
            Box(
                modifier = Modifier
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
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.converter_error_message),
                        style = TextStyle(
                            fontFamily = montserratFont,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = dpToSp(dp = 24.dp),
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clickable { },
                        text = stringResource(id = R.string.converter_retry_message),
                        style = TextStyle(
                            fontFamily = montserratFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = dpToSp(dp = 13.dp),
                            letterSpacing = 0.03.em
                        ),
                    )
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF9501FF)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .padding(top = 7.dp, end = 20.dp),
            content = {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back", tint = Color.White
            )
        },
            onClick = onClick)
        Text(
            modifier = Modifier
                .padding(top = 7.dp, end = 20.dp),
            text = SimpleDateFormat("d.MM.yy").format(Date()),
            style = MaterialTheme.typography.h1,
            fontSize = dpToSp(dp = 14.dp)
        )
    }

}

fun setTextForRoubles(input: String) : String {
    return when {
        input.isBlank() -> "рублей"
        input.contains('.') -> "рублей"
        input.endsWith("11") || input.endsWith("12") || input.endsWith("13") || input.endsWith("14") -> "рублей"
        input.endsWith('1') -> "рубль"
        input.endsWith('2') ||  input.endsWith('3') || input.endsWith('4')-> "рубля"
        else -> "рублей"
    }
}