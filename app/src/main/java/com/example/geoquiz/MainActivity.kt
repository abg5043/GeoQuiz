package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
            result ->
        // Handle the result
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        updateButtons()

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)

        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            updateButtons()
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            updateButtons()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            updateButtons()
        }

        updateQuestion()
    }

    private fun updateButtons() {
        if(quizViewModel.currentQuestionIsAnswered){
            disableButtons()
        } else {
            enableButtons()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer : Boolean) {
        quizViewModel.answerQuestion(userAnswer)
        val numQuestions = quizViewModel.numQuestions
        val correctAnswers = quizViewModel.correctAnswers

        val messageResId = when {
            quizViewModel.everyQuestionAnswered -> getString(
                R.string.score_toast,
                correctAnswers,
                numQuestions,
                (correctAnswers / numQuestions.toFloat() * 100.0).roundToInt(),
            )
            else -> {
                if(quizViewModel.currentQuestionAnswer == userAnswer) {
                    getString(R.string.correct_toast)
                } else getString(R.string.incorrect_toast)
            }
        }
        disableButtons()

        val resultMessage: Snackbar = Snackbar
            .make(binding.root, messageResId, Snackbar.LENGTH_LONG)

        resultMessage.show()
    }

    private fun disableButtons() {
        disableButton(binding.falseButton)
        disableButton(binding.trueButton)
        disableButton(binding.cheatButton)
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.isClickable = false
    }

    private fun enableButtons() {
        enableButton(binding.falseButton)
        enableButton(binding.trueButton)
        enableButton(binding.cheatButton)
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.isClickable = true
    }
}