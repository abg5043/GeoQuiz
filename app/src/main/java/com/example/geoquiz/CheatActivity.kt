package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityCheatBinding

private const val TAG = "CheatActivity"


private const val EXTRA_ANSWER_IS_TRUE =
    "com.example.geoquiz.answer_is_true"

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private var answerIsTrue: Boolean = false

    private val cheatViewModel: CheatViewModel by viewModels()

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a CheatViewModel: $cheatViewModel")

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener{
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else         -> R.string.false_button
            }
            cheatViewModel.buttonWasClicked = true
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

        if (cheatViewModel.buttonWasClicked) {
            binding.answerTextView.setText(R.string.true_button)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}