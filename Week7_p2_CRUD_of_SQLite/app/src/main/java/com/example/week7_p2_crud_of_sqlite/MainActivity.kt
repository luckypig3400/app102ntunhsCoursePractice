package com.example.week7_p2_crud_of_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var idInput:EditText
    lateinit var nameInput:EditText
    lateinit var output: TextView
    lateinit var insertBtn: Button
    lateinit var updateBtn: Button
    lateinit var deleteBtn: Button
    lateinit var readBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idInput = findViewById(R.id.idInput)
        nameInput = findViewById(R.id.nameInput)
        output = findViewById(R.id.output)
        insertBtn = findViewById(R.id.insertButton)
        updateBtn = findViewById(R.id.updateButton)
        deleteBtn = findViewById(R.id.deleteButton)
        readBtn = findViewById(R.id.readDataButton)


    }
}