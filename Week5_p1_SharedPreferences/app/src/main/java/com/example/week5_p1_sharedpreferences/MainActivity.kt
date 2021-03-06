package com.example.week5_p1_sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var storeBtn: Button
    private lateinit var showBtn: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        storeBtn = findViewById(R.id.storeDataBtn)
        showBtn = findViewById(R.id.showDataBtn)
        output = findViewById(R.id.output)

        storeBtn.setOnClickListener(storeBtnClicked)
        showBtn.setOnClickListener(showBtnClicked)
    }

    private var storeBtnClicked = View.OnClickListener {
        val MySetting = getSharedPreferences("UserDefault", 0)

        var string2write = MySetting.getString("StoredData", "")
        string2write += input.text.toString() + "\n"

        MySetting.edit()
            .putString("StoredData", string2write)
            .commit()
    }
    
    private var showBtnClicked = View.OnClickListener {
        val MySetting = getSharedPreferences("UserDefault", 0)
        output.text = MySetting.getString("StoredData", "")
    }
}