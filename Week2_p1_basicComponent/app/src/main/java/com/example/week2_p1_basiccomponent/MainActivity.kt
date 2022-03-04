package com.example.week2_p1_basiccomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var textView1:TextView
    private lateinit var button1:Button
    private lateinit var editText1: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 從資源類別R中取得介面元件
        textView1 = findViewById<TextView>(R.id.tv1)
        button1 = findViewById(R.id.btn1)
        editText1 = findViewById(R.id.editText1)

        textView1.text = "Hello Startup success!"

        button1.setOnClickListener(btn1onClick)
    }

    var btn1onClick = View.OnClickListener{
        textView1.text = editText1.text
    }
}