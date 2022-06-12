package com.example.week15_p2_canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val shapeView = ShapeView(this)
        setContentView(shapeView)
        // 設定老師自訂的ShapeView類別(Canvas)為APP首頁的ContentView
    }
}