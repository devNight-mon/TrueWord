package com.efesen.trueword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.efesen.trueword.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var startPlayButton: AppCompatButton
    private lateinit var howToPlay: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        bindUIComponent()

        startPlayButton.setOnClickListener {
            val intent = Intent(this, WordActivity::class.java)
            startActivity(intent)
        }

        howToPlay.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
    }

    private fun bindUIComponent() {
        with(binding) {
            startPlayButton = startButton
            howToPlay = howToPlayButton
        }
    }
}