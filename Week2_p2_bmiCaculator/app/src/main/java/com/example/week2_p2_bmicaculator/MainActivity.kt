package com.example.week2_p2_bmicaculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var btn1: Button
    private lateinit var output: TextView
    private lateinit var heightInput: EditText
    private lateinit var weightInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.btn1)
        output = findViewById(R.id.bmiTV)
        heightInput = findViewById(R.id.heightEditText)
        weightInput = findViewById(R.id.weightEditText)

        btn1.setOnClickListener(calculateBMI)
    }

    private var calculateBMI = View.OnClickListener {
        var height = heightInput.text.toString().toDouble() / 100
        var weight = weightInput.text.toString().toDouble()

        var bmi = weight / (height * height)
        // https://stackoverflow.com/questions/49011924/round-double-to-1-decimal-place-kotlin-from-0-044999-to-0-1
        bmi = String.format("%.3f", bmi).toDouble()

        if(bmi < 18.5){
            output.text = bmi.toString() + " 體重過輕"
        }else if(bmi < 24){
            output.text = bmi.toString() + " 體重正常"
        }else{
            output.text = bmi.toString() + " 體重過重"
        }
    }
}