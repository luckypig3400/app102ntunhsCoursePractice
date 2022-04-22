package com.example.week9_example2_multiactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var button1: Button
    lateinit var button5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.button1)
        button1.setOnClickListener(btn1ClickListener)

        button5 = findViewById(R.id.button5)
        button5.setOnClickListener(btn5ClickListener)
    }

    var btn1ClickListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MapsActivity::class.java)
        startActivity(intent)
    }

    val btn5ClickListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, BasicActivity2::class.java)
        startActivity(intent)
    }
}