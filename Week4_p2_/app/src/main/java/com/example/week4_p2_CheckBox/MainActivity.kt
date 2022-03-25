package com.example.week4_p2_CheckBox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

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

        button.setOnClickListener(btnClicked)
    }

    private val myCheckBoxes = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        val currentCheckBox: CheckBox = findViewById(buttonView.id)
        output.text = currentCheckBox.text.toString() + "已選取"
    }

    private val btnClicked = View.OnClickListener {

        output.text = "您已選擇:\n"

        if (checkBox1.isChecked) {
            output.text = output.text.toString() + checkBox1.text.toString() + "\n"
        }
        if (checkBox2.isChecked) {
            output.text = output.text.toString() + checkBox2.text.toString() + "\n"
        }
        if (checkBox3.isChecked) {
            output.text = output.text.toString() + checkBox3.text.toString() + "\n"
        }
    }
}