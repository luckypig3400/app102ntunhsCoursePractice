package com.example.week4_p1_radiobutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var radioGroup1: RadioGroup
    private lateinit var button: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup1 = findViewById(R.id.radioGroup1)
        button = findViewById(R.id.button)
        output = findViewById(R.id.output)

        // 設定Radio元件的Listener事件
        radioGroup1.setOnCheckedChangeListener(myRadioChecked)

        button.setOnClickListener(buttonClicked)
    }

    private val myRadioChecked = RadioGroup.OnCheckedChangeListener { group, checkedID ->
        val radioB: RadioButton = findViewById(checkedID)
        output.text = "剛按下" + radioB.text
    }

    private val buttonClicked = View.OnClickListener {
        val rGroupChecked: RadioButton = findViewById(radioGroup1.checkedRadioButtonId)
        output.text = "您選擇並送出的為:" + rGroupChecked.text
    }
}