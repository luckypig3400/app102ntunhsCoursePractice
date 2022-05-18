package com.example.week9_hw1_multiactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        navbarBtn.setOnClickListener(navbarBtnListener)
        mapBtn.setOnClickListener(mapBtnListener)
        coolBtn.setOnClickListener(coolBtnListener)
    }

    val navbarBtnListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

    var mapBtnListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MapsActivity::class.java)
        startActivity(intent)
    }

    var coolBtnListener = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, SomethingCoolActivity::class.java)
        startActivity(intent)
    }

}