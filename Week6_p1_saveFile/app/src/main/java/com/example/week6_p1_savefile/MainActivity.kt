package com.example.week6_p1_savefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var saveFileBtn: Button
    lateinit var loadFileBtn: Button
    lateinit var input: EditText
    lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveFileBtn = findViewById(R.id.saveFileButton)
        loadFileBtn = findViewById(R.id.loadFileButton)
        input = findViewById(R.id.input)
        output = findViewById(R.id.output)
    }
}