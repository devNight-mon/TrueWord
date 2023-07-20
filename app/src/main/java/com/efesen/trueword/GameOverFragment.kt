package com.efesen.trueword

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.efesen.trueword.databinding.DialogGameOverBinding

/**
 * Created by Efe Şen on 20.07.2023.
 */
class GameOverFragment: DialogFragment() {

    private lateinit var binding: DialogGameOverBinding

    private lateinit var correctScoreTextView: TextView
    private lateinit var wrongScoreTextView: TextView
    private lateinit var playAgainButton: AppCompatButton
    private lateinit var dismissButton: AppCompatButton

    private var correctCount: Int = 0
    private var wrongCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view?.setBackgroundColor(Color.TRANSPARENT)
        binding = DialogGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentDialog)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUIComponents()

        correctScoreTextView.text ="$correctCount"
        wrongScoreTextView.text =  "$wrongCount"
    }

    private fun bindUIComponents() {
        with(binding) {
            correctScoreTextView = correctCountTextview
            this@GameOverFragment.wrongScoreTextView = wrongCountTextview
            playAgainButton = playAgain
            dismissButton = closeButton
        }

        dismissButton.setOnClickListener { dismiss() }

        playAgainButton.setOnClickListener {startNewGame() }

    }

    private fun startNewGame() {
        val intent = Intent(requireContext(), WordActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        fun newInstance(correctCount: Int, wrongCount: Int): GameOverFragment {
            val fragment = GameOverFragment()
            fragment.correctCount = correctCount
            fragment.wrongCount = wrongCount
            return fragment
        }
    }
}