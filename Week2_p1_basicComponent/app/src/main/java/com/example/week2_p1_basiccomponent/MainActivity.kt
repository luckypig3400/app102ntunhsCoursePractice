package com.example.week2_p1_basiccomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView1:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 從資源類別R中取得介面元件
        textView1 = findViewById<TextView>(R.id.tv1);
        textView1.text = "Hello Startup success!";
    }
}