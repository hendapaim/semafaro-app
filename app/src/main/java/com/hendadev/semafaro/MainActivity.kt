package com.hendadev.semafaro

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hendadev.semafaro.ui.theme.SemafaroTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemafaroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Semafaro()
                }
            }
        }
    }
}

@Composable
fun Semafaro() {
    var sinal = 1
    var semafaroImage by remember {
        mutableStateOf(R.drawable.semafaros_1)
    }

    val tempo by rememberCountdownTimerState(60_000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Semafaro", style = MaterialTheme.typography.h2)
        when {
            (tempo in 10_000L downTo 1000L) -> {
                sinal = 2
                semafaroImage = semafaroSinal(sinal)
            }
            (tempo == 0L) -> {
                sinal = 3
                semafaroImage = semafaroSinal(sinal)
            }
        }
        Image(
            painter = painterResource(semafaroImage),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        Text("Tempo: ${tempo / 1000}", style = MaterialTheme.typography.h4)
    }
}

@Composable
fun rememberCountdownTimerState(
    initialMillis: Long,
    step: Long = 1000
): MutableState<Long> {
    val timeLeft = remember { mutableStateOf(initialMillis) }
    LaunchedEffect(initialMillis, step) {
        while (isActive && timeLeft.value > 0) {
            timeLeft.value = (timeLeft.value - step).coerceAtLeast(0)
            delay(step)
        }
    }
    return timeLeft
}

fun semafaroSinal(sinal: Int): Int {
    return when (sinal) {
        1 -> R.drawable.semafaros_1
        2 -> R.drawable.semafaros_2
        3 -> R.drawable.semafaros_3
        else -> R.drawable.semafaros_1
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SemafaroTheme {
        Semafaro()
    }
}