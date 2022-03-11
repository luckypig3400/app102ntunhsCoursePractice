package com.example.week3_p1_spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var button1: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get layout components from class R (resource)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        button1 = findViewById(R.id.btn1)
        output = findViewById(R.id.tv1)

        // Connect the content with ArrayAdapter
        var adapter_spinner1 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner1_content,
            android.R.layout.simple_spinner_item
        )

        // Set style for spinner1 (unnecessary)
        adapter_spinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Bind the adapter to spinner1
        spinner1.adapter = adapter_spinner1

    }
}