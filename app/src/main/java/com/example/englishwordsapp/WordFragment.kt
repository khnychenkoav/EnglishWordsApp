package com.example.englishwordsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class WordFragment : Fragment() {

    private lateinit var viewModel: WordViewModel

    private lateinit var tvEnglishWord: TextView
    private lateinit var etUserTranslation: EditText
    private lateinit var btnSubmitTranslation: Button
    private lateinit var tvFeedback: TextView
    private lateinit var btnNextWord: Button
    private lateinit var btnRestartGame: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvEnglishWord = view.findViewById(R.id.tvEnglishWord)
        etUserTranslation = view.findViewById(R.id.etUserTranslation)
        btnSubmitTranslation = view.findViewById(R.id.btnSubmitTranslation)
        tvFeedback = view.findViewById(R.id.tvFeedback)
        btnNextWord = view.findViewById(R.id.btnNextWord)
        btnRestartGame = view.findViewById(R.id.btnRestartGame)
        progressBar = view.findViewById(R.id.progressBar)

        viewModel = ViewModelProvider(this)[WordViewModel::class.java]

        viewModel.currentWord.observe(viewLifecycleOwner) { word ->
            tvEnglishWord.text = word.english
        }

        viewModel.answerFeedback.observe(viewLifecycleOwner) { feedback ->
            tvFeedback.text = feedback
        }

        viewModel.showNextButton.observe(viewLifecycleOwner) { show ->
            btnNextWord.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.showTranslationButton.observe(viewLifecycleOwner) { show ->
            btnSubmitTranslation.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.showRestartButton.observe(viewLifecycleOwner) { show ->
            btnRestartGame.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.isEtUserTranslationVisible.observe(viewLifecycleOwner) { show ->
            etUserTranslation.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            btnSubmitTranslation.isEnabled = !loading
        }

        btnSubmitTranslation.setOnClickListener {
            val userInput = etUserTranslation.text.toString()
            btnSubmitTranslation.isVisible = false
            viewModel.checkTranslation(userInput)
        }

        btnNextWord.setOnClickListener {
            btnSubmitTranslation.isVisible = true
            viewModel.nextWord()
            etUserTranslation.text.clear()
        }

        btnRestartGame.setOnClickListener {
            viewModel.restartGame()
            etUserTranslation.text.clear()
        }
    }
}
