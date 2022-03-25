package com.example.week4_p1_radiobutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var radioGroup1: RadioGroup
    private lateinit var button: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup1 = findViewById(R.id.radioGroup1)
        button = findViewById(R.id.button)
        output = findViewById(R.id.output)

    }
}