package com.example.week2_hw1_bingogame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var imgBtn: ImageButton
    private lateinit var output: TextView

    private var bingoNumber = Random.nextInt(1, 101)
    //  https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial layout components
        input = findViewById(R.id.input)
        imgBtn = findViewById(R.id.imageButton)
        output = findViewById(R.id.output)

        output.text = bingoNumber.toString()
    }

//    private var buttonBingoGame = View.OnClickListener {
//
//    }

}