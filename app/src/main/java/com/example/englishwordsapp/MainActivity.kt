package com.example.englishwordsapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

data class Word (val english: String, val translation: String)

class MainActivity : AppCompatActivity() {

    private lateinit var btnWords: Button
    private lateinit var btnLifecycle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnWords = findViewById(R.id.btnWords)
        btnLifecycle = findViewById(R.id.btnLifecycle)

        replaceFragment(WordFragment())

        btnWords.setOnClickListener {
            replaceFragment(WordFragment())
        }

        btnLifecycle.setOnClickListener {
            replaceFragment(LifecycleFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

    }
}