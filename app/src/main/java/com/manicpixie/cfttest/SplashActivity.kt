package com.manicpixie.cfttest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.manicpixie.cfttest.ui.theme.CFTTestTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backgroundAnimatable = Animatable(Color.White)
        setContent {
            CFTTestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundAnimatable.value),
                    contentAlignment = Alignment.Center) {
                        val scope = rememberCoroutineScope()
                        scope.launch{

                            backgroundAnimatable.animateTo(targetValue = Color(0xFF9E00FF), animationSpec = tween(
                                durationMillis = 150
                            ))
                        }
                        Image(painter = painterResource(id = R.drawable.splash_icon), contentDescription = "Splash",
                        modifier = Modifier.size(125.dp))
                    }
                }
            }
        }
        val activity = this
        lifecycleScope.launch(Dispatchers.Main){
            delay(2500)
            startActivity(Intent(activity, MainActivity::class.java))
            finish()
        }

    }
}