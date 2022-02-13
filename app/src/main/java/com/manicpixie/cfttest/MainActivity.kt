package com.manicpixie.cfttest


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.manicpixie.cfttest.presentation.main.MainScreen
import com.manicpixie.cfttest.ui.theme.CFTTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CFTTestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}