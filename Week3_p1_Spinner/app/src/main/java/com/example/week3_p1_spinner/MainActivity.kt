package com.example.week3_p1_spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var button1: Button
    private lateinit var output: TextView
    private lateinit var debugInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get layout components from class R (resource)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        button1 = findViewById(R.id.btn1)
        output = findViewById(R.id.tv1)
        debugInfo = findViewById(R.id.debuginfo)

        // Connect the content with ArrayAdapter
        var adapterSpinner1 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner1_content,
            android.R.layout.simple_spinner_item
        )

        // Set style for spinner1 (unnecessary)
        adapterSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Bind the adapter to spinner1
        spinner1.adapter = adapterSpinner1

        // EventListener for spinner1 (unnecessary)
        spinner1.onItemSelectedListener = SpinnerSelectedListener_spinner1

        // Generate items for spinner2
        var items2 = arrayOfNulls<String>(6969)
        for (i in 0..items2.size - 1) {
            items2[i] = "第" + (i + 1) + "號"
        }
        // Connect the content with ArrayAdapter for spinner2
        val adapterSpinner2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)
        // Bind Adapter to Spinner2
        spinner2.adapter = adapterSpinner2

        // Bind button1 onClicke Listener
        button1.setOnClickListener(btn1Listener)
    }

    // EventListener for button1
    private var btn1Listener = View.OnClickListener {
        output.text = "您選擇的為:\n[" + spinner1.getSelectedItemPosition() + "]" + spinner1.getSelectedItem() +
                "\n[" + spinner2.getSelectedItemPosition() + "]" + spinner2.getSelectedItem()
    }

    // EventListener for spinner1 (unnecessary)
    private val SpinnerSelectedListener_spinner1: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                // 選到時做的事 (pos會傳回第N個整數)
                debugInfo.text = "Spinner1 eListener:" + parent?.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 都沒被選到時
                debugInfo.text = "Nothing selected"
            }
        }
}