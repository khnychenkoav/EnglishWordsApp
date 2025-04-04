package com.example.englishwordsapp

import android.content.Context

object ProgressManager {
    private const val PREFS_NAME = "english_words_progress"
    private const val KEY_CURRENT_WORD_INDEX = "current_word_index"
    private const val KEY_TRANSLATION_VISIBLE = "translation_visible"

    fun saveProgress (context: Context, currentWordIndex: Int, translationVisible: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt(KEY_CURRENT_WORD_INDEX, currentWordIndex)
            putBoolean(KEY_TRANSLATION_VISIBLE, translationVisible)
            apply()
        }
    }

    fun loadProgress (context: Context): Pair<Int, Boolean>? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (!prefs.contains(KEY_CURRENT_WORD_INDEX) || !prefs.contains(KEY_TRANSLATION_VISIBLE)) {
            return null
        }
        val currentWordIndex = prefs.getInt(KEY_CURRENT_WORD_INDEX, 0)
        val translationVisible = prefs.getBoolean(KEY_TRANSLATION_VISIBLE, false)
        return Pair(currentWordIndex, translationVisible)

    }
}