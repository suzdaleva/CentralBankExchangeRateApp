package com.manicpixie.cfttest.presentation.converter.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.manicpixie.cfttest.presentation.currencies_list.components.clearFocusOnKeyboardDismiss
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.*

@Composable
fun InputField(
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    currencyName: String,
    hint: String = "",
    text: String = "",
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(122.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            BasicTextField(
                enabled = enabled,
                singleLine = true,
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                cursorBrush = SolidColor(Blue),
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = dpToSp(dp = 30.dp),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    letterSpacing = 0.04.em,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clearFocusOnKeyboardDismiss()
                    .padding(end = 12.dp, bottom = 6.dp)
                    .onFocusChanged { onFocusChange(it) }

            )
            if (isHintVisible) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp, bottom = 6.dp),
                    text = hint,
                    style = TextStyle(
                        fontSize = dpToSp(dp = 30.dp),
                        fontFamily = montserratFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        letterSpacing = 0.04.em
                    )
                )
            }
            Text(
                modifier = Modifier
                    .padding(end = 12.dp, bottom = 10.dp)
                    .align(Alignment.BottomEnd),
                text = currencyName,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(dp = 15.dp)
                ),
                color = Color.Black
            )

        }
    }
}
