package com.example.week4_p2_CheckBox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var output: TextView
    private lateinit var button: Button
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        output = findViewById(R.id.output)
        button = findViewById(R.id.button)
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)

        // 設定CheckBox元件的Listener事件
        checkBox1.setOnCheckedChangeListener(myCheckBoxes)
        checkBox2.setOnCheckedChangeListener(myCheckBoxes)
        checkBox3.setOnCheckedChangeListener(myCheckBoxes)
    }

    private val myCheckBoxes = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when(buttonView.id){
            R.id.checkBox1 -> {
                output.text = checkBox1.text.toString() + "已選取"
            }
            R.id.checkBox2 -> {
                output.text = checkBox2.text.toString() + "已選取"
            }
            R.id.checkBox3 -> {
                output.text = checkBox3.text.toString() + "已選取"
            }
        }
    }
}