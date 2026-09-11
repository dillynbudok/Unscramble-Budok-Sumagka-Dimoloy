package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UnscrambleTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {

    val gameViewModel = remember {
        GameViewModel()
    }

    var uiState by remember {
        mutableStateOf(gameViewModel.uiState)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "UNSCRAMBLE",
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = uiState.scrambledWord,
            fontSize = 40.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Unscramble the word!",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = uiState.userAnswer,
            onValueChange = {
                gameViewModel.updateUserAnswer(it)
                uiState = gameViewModel.uiState
            },
            label = {
                Text(
                    text = "Enter your answer",
                    color = Color.Black
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    gameViewModel.submitAnswer()
                    uiState = gameViewModel.uiState
                }
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                gameViewModel.submitAnswer()
                uiState = gameViewModel.uiState
            }
        ) {
            Text(
                text = "SUBMIT",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Score: ${uiState.score} / 10",
            color = Color.Black
        )
    }
}