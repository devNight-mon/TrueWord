package com.efesen.trueword

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.efesen.trueword.databinding.ActivityWordBinding
import kotlin.random.Random as Random

class WordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordBinding

    private lateinit var backButton: AppCompatButton
    private lateinit var restartButton: AppCompatButton
    private lateinit var timeTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var wordTextview: TextView
    private lateinit var correctButton: AppCompatButton
    private lateinit var wrongButton: AppCompatButton

    private val wordList = listOf<String>(
        "Assuage", "Corroborate", "Precipitate", "Equivocal", "Immediate",
        "Minute", "Recommend", "Illuminate", "Ephemeral", "Obfuscate",
        "Deliberate", "Ubiquitous", "Insidious", "Superfluous", "Indict",
        "Prestidigitation", "Lachrymose", "Borborygmus", "Ebullient", "Quixotic",
        "Perspicacious","Sycophant","Exacerbate","Incongruous","Malevolent")

    private var currentWord: String = ""
    private var correctCount: Int = 0
    private var wrongCount: Int = 0
    private val wrongOptions = mutableListOf<String>()
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 30000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        bindUIComponent()

        // Show the first word to start the game
        showRandomWord()

        handleButtonClick()
    }

    private fun bindUIComponent() {
        with(binding) {
            this@WordActivity.backButton = backButton
            this@WordActivity.restartButton = restartButton
            this@WordActivity.timeTextView = timerTextview
            this@WordActivity.scoreTextView = scoreTextview
            this@WordActivity.wordTextview = wordTextview
            this@WordActivity.correctButton = correctButton
            this@WordActivity.wrongButton = wrongButton
        }
    }

    private fun showRandomWord() {
        val randomIndex = Random.nextInt(wordList.size)
        currentWord =  wordList[randomIndex]

        // Creating Incorrect Archive
            generateWrongOptions()

        // Show Word
        wordTextview.text = currentWord

        // start Timer
        startTimer()
    }

    private fun handleButtonClick() {

        restartButton.setOnClickListener { restartGame() }

        correctButton.setOnClickListener { checkAnswer(currentWord) }

        backButton.setOnClickListener { finish() }

        wrongButton.setOnClickListener {
            val randomWrongOption = wrongOptions.random()
            checkAnswer(randomWrongOption)
        }
    }

    private fun startTimer() {
        countDownTimer = object: CountDownTimer(timeLeftInMillis ,1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateCountDownText()
            }
        }.start()

    }

    private fun updateCountDownText() {
        val seconds = timeLeftInMillis / 1000
        val formattedTime = String.format("00:%02d", seconds)
        timeTextView.text = formattedTime
    }

    private fun checkAnswer(selectedOptions: String) {
        if (selectedOptions == currentWord) {
            correctCount++
            correctButton.setBackgroundResource(R.drawable.word_correct_button)
            correctButton.setTextColor(Color.parseColor("#ec5002"))

            // Update the scoreTextView with the correct count
            scoreTextView.text = correctCount.toString()
        } else {
           wrongCount++
            wrongButton.setBackgroundResource(R.drawable.word_wrong_button)
            wrongButton.setTextColor(Color.parseColor("#FF0000"))
        }

      //  Color of the true and false buttons after clicking the true and false buttons
     //   after 1 second change it back to the starting colors
        Handler(Looper.getMainLooper()).postDelayed({
            correctButton.setBackgroundResource(R.drawable.unanswered_button_text)
            correctButton.setTextColor(Color.WHITE)

            wrongButton.setBackgroundResource(R.drawable.unanswered_button_text)
            wrongButton.setTextColor(Color.WHITE)

            // Show game over popup if the time is up or the game is over
            if (timeLeftInMillis <= 0 || correctCount + wrongCount >= wordList.size) {
                showGameOverPopup()
            }
        },1000)

        showRandomWord()

    }

    private fun generateWrongOptions() {
        wrongOptions.clear()

        val filteredList =  wordList.filter { it != currentWord }

       // Create two false attributes different from word lists
        val random = Random
        while (wrongOptions.size < 2) {
            val randomIndex = random.nextInt(filteredList.size)
            val wrongOption = filteredList[randomIndex]
            wrongOptions.add(wrongOption)
        }
    }

    private fun restartGame() {
        correctCount = 0
        wrongCount = 0
        scoreTextView.text = "0"

        // Restart Timer
        countDownTimer?.cancel()
        countDownTimer  = null
       timeLeftInMillis = 30000

        updateCountDownText()

        showRandomWord()

        correctButton.setBackgroundResource(R.drawable.unanswered_button_text)
        correctButton.setTextColor(Color.WHITE)
        wrongButton.setBackgroundResource(R.drawable.unanswered_button_text)
        wrongButton.setTextColor(Color.WHITE)
    }

    private fun showGameOverPopup() {
        val gameOverFragment = GameOverFragment.newInstance(correctCount, wrongCount)
        gameOverFragment.show(supportFragmentManager, "game_over_fragment")
    }
}