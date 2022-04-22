package com.example.week9_intent_and_multiactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jumpToLayout02()
    }

    fun jumpToLayout02() {
        setContentView(R.layout.activity_slave)
        // 設定button元件的Listener事件
        this.findViewById<Button>(R.id.button2).setOnClickListener(View.OnClickListener {
            this@MainActivity.jumpToLayout01()
        })
    }

    fun jumpToLayout01() {
        setContentView(R.layout.activity_main)
        // 設定button元件的Listener事件
        this.findViewById<Button>(R.id.button1).setOnClickListener(View.OnClickListener {
            this@MainActivity.jumpToLayout02()
        })
    }
}

