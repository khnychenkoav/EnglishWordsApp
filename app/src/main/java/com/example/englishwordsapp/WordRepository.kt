package com.example.englishwordsapp

object WordRepository {

    private val words = listOf(
        Word("Hello", "Привет"),
        Word("Goodbye", "Пока"),
        Word("Apple", "Яблоко"),
        Word("Juice", "Сок"),
    )

    fun getRandomWord(): Word = words.random()

    fun getWordByIndex(index: Int): Word = words[index % words.size]

    fun getIndexOfWord(word: Word): Int = words.indexOf(word)

    fun wordsCount(): Int = words.size
}