package com.manicpixie.cfttest.presentation.currencies_list.components

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.Blue

@ExperimentalAnimationApi
@Composable
fun SearchBar(
    isHintVisible: Boolean = true,
    hint: String = "",
    text: String = "",
    onFocusChange: (FocusState) -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = true,
    leadingIcon: (@Composable() () -> Unit)? = null,
) {

    Row(
        modifier = modifier.border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(18.dp))
            .height(36.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(
            modifier = modifier
                .weight(1f)
        ) {
            BasicTextField(
                value = text,
                cursorBrush = SolidColor(Blue),
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = textStyle,
                modifier = Modifier
                    .clearFocusOnKeyboardDismiss()
                    .fillMaxWidth()
                    .onFocusChanged { onFocusChange(it) }

            )
            if (isHintVisible) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.body1,
                    fontSize = dpToSp(dp = 14.dp)
                )
            }
        }
    }

}


fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * 0.15
}

@Composable
fun rememberIsKeyboardOpen(): State<Boolean> {
    val view = LocalView.current

    return produceState(initialValue = view.isKeyboardOpen()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener { value = view.isKeyboardOpen() }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }
}

fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {

    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val isKeyboardOpen by rememberIsKeyboardOpen()

        val focusManager = LocalFocusManager.current
        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}