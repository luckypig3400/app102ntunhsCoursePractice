package com.example.week9_example2_multiactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class BasicActivity2 : AppCompatActivity() {
    lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic2)

        button3 = findViewById(R.id.button3)
        button3.setOnClickListener(btn3ClickListener)
    }

    val btn3ClickListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }
}