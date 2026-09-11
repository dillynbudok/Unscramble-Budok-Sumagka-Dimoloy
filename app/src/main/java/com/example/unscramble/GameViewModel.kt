package com.example.unscramble

import androidx.lifecycle.ViewModel
import java.util.Collections

class GameViewModel : ViewModel() {

    private val words = listOf(
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

    private var currentWordIndex = 0
    private var score = 0
    private var userAnswer = ""

    private var scrambledWord = scrambleWord(words[currentWordIndex])

    val uiState: GameUiState
        get() = GameUiState(
            scrambledWord = scrambledWord,
            userAnswer = userAnswer,
            score = score
        )

    fun updateUserAnswer(answer: String) {
        userAnswer = answer
    }

    fun submitAnswer() {

        if (userAnswer.trim().uppercase() == words[currentWordIndex]) {

            score++
            userAnswer = ""

            if (currentWordIndex < words.size - 1) {
                currentWordIndex++
            } else {
                currentWordIndex = 0
                score = 0
            }

            scrambledWord = scrambleWord(words[currentWordIndex])
        }
    }

    private fun scrambleWord(word: String): String {

        val letters = word.toCharArray().toMutableList()

        do {
            Collections.shuffle(letters)
        } while (
            letters.joinToString("") == word &&
            word.length > 1
        )

        return letters.joinToString("")
    }
}