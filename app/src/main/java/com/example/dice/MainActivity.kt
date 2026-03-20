package com.example.dice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceScreen()
        }
    }
}

@Composable
@Preview
fun DiceScreen() {
    val scope = rememberCoroutineScope()

    var dice1 by remember { mutableIntStateOf(1) }
    var dice2 by remember { mutableIntStateOf(1) }
    var dice3 by remember { mutableIntStateOf(1) }
    var dice4 by remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .weight(0.05f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Кости", color = Color.Red, fontSize = 30.sp)
        }

        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
        ) {
            DiceBox(dice1, Modifier.weight(1f))
            DiceBox(dice2, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
        ) {
            DiceBox(dice3, Modifier.weight(1f))
            DiceBox(dice4, Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .weight(0.07f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                scope.launch {
                    dice1 = rollDice(1)
                    delay(1000)

                    dice2 = rollDice(2)
                    delay(1000)

                    dice3 = rollDice(3)
                    delay(1000)

                    dice4 = rollDice(4)
                }
            }) {
                Text(text = stringResource(id = R.string.thrown), fontSize = 24.sp)
            }
        }

        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun DiceBox(value: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(1.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getDiceImage(value)),
            contentDescription = value.toString(),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f)
        )
    }
}

fun getDiceImage(value: Int): Int {
    return when (value) {
        1 -> R.drawable.dice1
        2 -> R.drawable.dice2
        3 -> R.drawable.dice3
        4 -> R.drawable.dice4
        5 -> R.drawable.dice5
        else -> R.drawable.dice6
    }
}

fun rollDice(diceNumber: Int): Int {
    val probabilities = when (diceNumber) {

        1 -> listOf(
            1.0 / 6,
            1.0 / 2,
            1.0 / 12,
            1.0 / 12,
            1.0 / 24,
            3.0 / 24
        )

        2 -> listOf(
            1.0 / 3,
            1.0 / 6,
            1.0 / 12,
            1.0 / 6,
            1.0 / 12,
            2.0 / 12
        )

        3 -> List(6) { 1.0 / 6 }

        // Кубик 4
        4 -> listOf(
            5.0 / 6,
            1.0 / 48,
            1.0 / 48,
            1.0 / 48,
            1.0 / 24,
            1.0 / 16
        )

        else -> List(6) { 1.0 / 6 }
    }

    val r = Random.nextDouble()
    var cumulative = 0.0

    for (i in probabilities.indices) {
        cumulative += probabilities[i]
        if (r < cumulative) {
            return i + 1
        }
    }

    return 6
}