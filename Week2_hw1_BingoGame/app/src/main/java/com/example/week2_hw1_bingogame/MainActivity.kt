package com.example.week2_hw1_bingogame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var bingoBtn: Button
    private lateinit var output: TextView

    private var bingoNumber = Random.nextInt(1, 101)
    //  https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial layout components
        input = findViewById(R.id.input)
        bingoBtn = findViewById(R.id.bingoBtn)
        output = findViewById(R.id.output)

        // Bind onClick Event Listener to imageButton
        bingoBtn.setOnClickListener(buttonBingoGame)
    }

    private var buttonBingoGame = View.OnClickListener {
        var num = input.text.toString().toInt()

        if (num > bingoNumber) {
            output.text = "數值太大"
        } else if (num == bingoNumber) {
            output.text = "Bingo~!"
            input.isEnabled = false

            if (bingoBtn.text.toString() == "再來一局") {
                bingoNumber = Random.nextInt(1, 101)

                output.text = "來吧~繼續遊玩猜數字( •̀ ω •́ )✧"
                bingoBtn.text = "我猜是這個數字"
                input.isEnabled = true
            }else{
                bingoBtn.text = "再來一局"
            }
        } else {
            output.text = "數值太小"
        }

    }

}

// 試設計一 Bingo 遊戲，讓使用者輸入 1~100 間的一個值，並提示「數值太大」、「數值太小」或「 Bingo 」