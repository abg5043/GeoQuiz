package com.example.geoquiz

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )


    private var currentIndex = 0
    private var answeredQuestions: Int = 0

    var correctAnswers: Int = 0
        private set

    val everyQuestionAnswered : Boolean
        get() = answeredQuestions == questionBank.size

    val currentQuestionAnswer : Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText : Int
        get() = questionBank[currentIndex].textResId

    val currentQuestion : Question
        get() = questionBank[currentIndex]

    val numQuestions : Int
        get() = questionBank.size

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    fun moveToPrev() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun answerQuestion(userAnswer : Boolean) {
        currentQuestion.isAnswered = true
        answeredQuestions++
        if(userAnswer == currentQuestionAnswer) correctAnswers++
    }
}