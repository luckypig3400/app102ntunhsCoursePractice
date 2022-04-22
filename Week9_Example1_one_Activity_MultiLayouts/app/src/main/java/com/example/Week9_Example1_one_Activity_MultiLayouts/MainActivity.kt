package com.example.Week9_Example1_one_Activity_MultiLayouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = layoutInflater

        val view1: View = inflater.inflate(R.layout.activity_main, null) //找出第一個視窗
        val view2: View = inflater.inflate(R.layout.activity_slave, null) //找出第二個視窗
        setContentView(view1) //顯示第一個視窗

        val button1 = view1.findViewById<View>(R.id.button1) as Button //第一個視窗的按鈕
        button1.setOnClickListener(View.OnClickListener () {
            setContentView(view2) //按了之後跳到第2個視窗
        })

        val button2 = view2.findViewById<View>(R.id.button2) as Button //第二個視窗的按鈕
        button2.setOnClickListener(View.OnClickListener() {
            setContentView(view1) //按了之後跳到第1個視窗
        })
    }

    /*
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
    */
}
