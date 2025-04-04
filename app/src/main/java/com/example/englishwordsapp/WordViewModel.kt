package com.example.englishwordsapp

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentWord = MutableLiveData<Word>()
    val currentWord: MutableLiveData<Word>
        get() = _currentWord

    private val _answerFeedback = MutableLiveData<String>()
    val answerFeedback: MutableLiveData<String>
        get() = _answerFeedback

    private val _showNextButton = MutableLiveData<Boolean>(false)
    val showNextButton: MutableLiveData<Boolean>
        get() = _showNextButton

    private val _showTranslationButton = MutableLiveData<Boolean>(true)
    val showTranslationButton: MutableLiveData<Boolean>
        get() = _showTranslationButton

    private val _showRestartButton = MutableLiveData<Boolean>(false)
    val showRestartButton: MutableLiveData<Boolean>
        get() = _showRestartButton

    private val _isTranslationVisible = MutableLiveData<Boolean>(false)
    val isTranslationVisible: MutableLiveData<Boolean>
        get() = _isTranslationVisible

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: MutableLiveData<Boolean>
        get() = _isLoading

    private val _isEtUserTranslationVisible = MutableLiveData<Boolean>(true)
    val isEtUserTranslationVisible: MutableLiveData<Boolean>
        get() = _isEtUserTranslationVisible

    private val solvedWords = mutableSetOf<Int>()

    init {
        val progress = ProgressManager.loadProgress(getApplication())
        if (progress != null) {
            _currentWord.value = WordRepository.getWordByIndex(progress.first)
            _isTranslationVisible.value = progress.second
        } else {
            _currentWord.value = WordRepository.getRandomWord()
        }

    }

    fun checkTranslation(userInput: String) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(1500)
            val correctTranslation = _currentWord.value?.translation ?: ""
            if (userInput.trim().equals(correctTranslation, ignoreCase = true)) {
                _answerFeedback.value = "Правильно! Перевод: $correctTranslation"
                _showNextButton.value = true

                val index = WordRepository.getIndexOfWord(_currentWord.value!!)
                solvedWords.add(index)
                ProgressManager.saveProgress(getApplication(), index, true)
            } else {
                _answerFeedback.value = "Неправильно. Верный перевод: $correctTranslation!"
                _showNextButton.value = true
            }
            _isEtUserTranslationVisible.value = false
            _isLoading.value = false

        }
    }

    fun nextWord() {
        val allIndexes = (0 until WordRepository.wordsCount())
        val unsolvedIndexes = allIndexes.filter { it !in solvedWords }

        if (unsolvedIndexes.isEmpty()) {
            _answerFeedback.value = "Поздравляем, все слова переведены!"
            _showTranslationButton.value = false
            _showNextButton.value = false
            _showRestartButton.value = true

        } else {
            _isEtUserTranslationVisible.value = true
            val randomIndex = unsolvedIndexes.random()
            _currentWord.value = WordRepository.getWordByIndex(randomIndex)
            _answerFeedback.value = ""
            _showNextButton.value = false
            ProgressManager.saveProgress(getApplication(), randomIndex, false)
        }
    }

    fun restartGame() {
        solvedWords.clear()
        _currentWord.value = WordRepository.getRandomWord()
        _answerFeedback.value = ""
        _showNextButton.value = false
        _showRestartButton.value = false
        _showTranslationButton.value = true
        _isTranslationVisible.value = false
        _isEtUserTranslationVisible.value = true
        ProgressManager.saveProgress(getApplication(), WordRepository.getIndexOfWord(_currentWord.value!!), false)
    }

    fun onShowTranslationClicked() {
        if (_isTranslationVisible.value == false) {
            _isLoading.value = true
            viewModelScope.launch {
                delay(2000)
                _isTranslationVisible.value = true
                _isLoading.value = false
                val index = WordRepository.getIndexOfWord(_currentWord.value!!)
                ProgressManager.saveProgress(getApplication(), index, true)
            }
        } else {
            _isTranslationVisible.value = false
            val newWord = WordRepository.getRandomWord()
            _currentWord.value = newWord
            ProgressManager.saveProgress(getApplication(), WordRepository.getIndexOfWord(newWord), false)
        }
    }
}