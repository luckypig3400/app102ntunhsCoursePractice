package com.example.week2_p2_bmicaculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var btn1:Button
    private lateinit var output:TextView
    private lateinit var heightInput:EditText
    private lateinit var weightInput:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.btn1)
        output = findViewById(R.id.bmiTV)
        heightInput = findViewById(R.id.heightEditText)
        weightInput = findViewById(R.id.weightEditText)

    }
}