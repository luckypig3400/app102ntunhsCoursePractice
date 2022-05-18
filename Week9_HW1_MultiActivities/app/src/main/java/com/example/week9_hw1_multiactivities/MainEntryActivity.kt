package com.example.week9_hw1_multiactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainEntryActivity : AppCompatActivity() {
    private lateinit var navbarBtn: Button
    private lateinit var mapBtn: Button
    private lateinit var coolBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_entry)

        navbarBtn = findViewById(R.id.navbarActButton)
        mapBtn = findViewById(R.id.mapActButton)
        coolBtn = findViewById(R.id.rwdActButton)
    }
}