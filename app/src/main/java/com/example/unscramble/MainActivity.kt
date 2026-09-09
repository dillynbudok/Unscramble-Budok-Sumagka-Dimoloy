package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.unit.sp
import com.example.unscramble.ui.theme.UnscrambleTheme
import java.util.Collections

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

fun scrambleWord(word: String): String {
    val letters = word.toCharArray().toMutableList()

    do {
        Collections.shuffle(letters)
    } while (letters.joinToString("") == word && word.length > 1)

    return letters.joinToString("")
}

@Composable
fun GameScreen() {

    var userAnswer by remember {
        mutableStateOf("")
    }

    val words = listOf(
        "CAT",
        "DOG",
        "BOOK",
        "TREE",
        "FISH",
        "HOUSE",
        "WATER",
        "APPLE",
        "PHONE",
        "SCHOOL"
    )

    var currentWordIndex by remember {
        mutableStateOf(0)
    }
    var score by remember {
        mutableStateOf(0)
    }

    var scrambledWord by remember {
        mutableStateOf(
            scrambleWord(words[0])
        )
    }

    fun submitAnswer() {

        if (userAnswer.trim().uppercase() == words[currentWordIndex]) {

            score++

            if (currentWordIndex == words.size - 1) {

                currentWordIndex = 0
                score = 0
                userAnswer = ""
                scrambledWord = scrambleWord(words[0])

            } else {

                currentWordIndex++

                userAnswer = ""

                scrambledWord = scrambleWord(
                    words[currentWordIndex]
                )
            }
        }
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

        Text(
            text = scrambledWord,
            fontSize = 40.sp,
            color = Color.Black
        )

        Text(
            text = "Unscramble the word!",
            color = Color.Black
        )

        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
            },
            label = {
                Text(
                    text = "Enter your answer",
                    color = Color.Black
                )
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    submitAnswer()
                }
            )
        )

        Button(
            onClick = {
                submitAnswer()
            }
        ) {
            Text(
                text = "SUBMIT",
                color = Color.Black
            )
        }

        Text(
            text = "Score: $score / 10",
            color = Color.Black
        )
    }
}