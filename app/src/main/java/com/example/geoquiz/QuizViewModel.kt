package com.example.geoquiz

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val ANSWERED_QUESTIONS_KEY = "ANSWERED_QUESTIONS_KEY"
const val CORRECT_ANSWERS_KEY = "CORRECT_ANSWERS_KEY"
const val ANSWERED_QUESTIONS_LIST = "ANSWERED_QUESTIONS_LIST"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    private var answeredQuestionsList: MutableList<Boolean>
        get() = savedStateHandle.get(ANSWERED_QUESTIONS_LIST) ?: mutableListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            )
        set(value) = savedStateHandle.set(ANSWERED_QUESTIONS_LIST, value)

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )


    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var answeredQuestions: Int
        get() = savedStateHandle.get(ANSWERED_QUESTIONS_KEY) ?: 0
        set(value) = savedStateHandle.set(ANSWERED_QUESTIONS_KEY, value)

    var correctAnswers: Int
        get() = savedStateHandle.get(CORRECT_ANSWERS_KEY) ?: 0
        private set(value) = savedStateHandle.set(CORRECT_ANSWERS_KEY, value)

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

    val currentQuestionIsAnswered : Boolean
        get() = answeredQuestionsList[currentIndex]

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
        var tempIsAnsweredList = answeredQuestionsList
        tempIsAnsweredList[currentIndex] = true
        answeredQuestionsList = tempIsAnsweredList
        answeredQuestions++
        if(userAnswer == currentQuestionAnswer) correctAnswers++
    }
}