package com.example.week5_quiz_numberclassifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var output: TextView
    lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.button1)
        input = findViewById(R.id.input)
        output = findViewById(R.id.output)

        btn1.setOnClickListener(btn1ClickListener)
    }

    var btn1ClickListener = View.OnClickListener {
        var num = input.text.toString().toInt()

        if (num == 0) {
            output.text = "此數為零"
        } else if (num < 0) {
            if (num % 2 == 1) {
                output.text = input.text.toString() + "為負奇數"
            } else {
                output.text = input.text.toString() + "為負偶數"
            }
        } else {
            if (num % 2 == 1) {
                output.text = input.text.toString() + "為正奇數"
            } else {
                output.text = input.text.toString() + "為正偶數"
            }
        }
    }
}