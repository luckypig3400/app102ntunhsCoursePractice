package com.example.week9_hw1_multiactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainEntryActivity : AppCompatActivity() {
    private lateinit var coolBtn: Button
    private lateinit var mapBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_entry)

//        coolBtn = findViewById(R.id.coolButton)
//        mapBtn = findViewById(R.id.mapButton)
    }
}