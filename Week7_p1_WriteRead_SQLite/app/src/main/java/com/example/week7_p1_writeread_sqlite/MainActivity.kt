package com.example.week7_p1_writeread_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var writeBtn: Button
    private lateinit var readBtn: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        writeBtn = findViewById(R.id.writeDBbutton)
        readBtn = findViewById(R.id.readDBbutton)
        output = findViewById(R.id.output)

        writeBtn.setOnClickListener(writeDBbuttonClicked)
        readBtn.setOnClickListener(readDBbuttonClicked)
    }

    private val writeDBbuttonClicked = View.OnClickListener {

    }

    private val readDBbuttonClicked = View.OnClickListener {
        
    }
}