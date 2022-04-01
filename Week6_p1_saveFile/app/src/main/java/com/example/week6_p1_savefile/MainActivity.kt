package com.example.week6_p1_savefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.*

class MainActivity : AppCompatActivity() {
    lateinit var saveFileBtn: Button
    lateinit var loadFileBtn: Button
    lateinit var input: EditText
    lateinit var output: TextView

    lateinit var filePath:String
    lateinit var file1Name:String
    lateinit var file2Name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveFileBtn = findViewById(R.id.saveFileButton)
        loadFileBtn = findViewById(R.id.loadFileButton)
        input = findViewById(R.id.input)
        output = findViewById(R.id.output)

        filePath = getFilesDir().toString() + "/FileSavingTest/"
        file1Name = filePath + "file1.txt"
        file2Name = filePath + "file2.txt"

        val dirLocation = File(filePath)
        if (!dirLocation.exists()) {
            // Auto create dir if not exists
            dirLocation.mkdirs()
        }

        loadFileBtn.setOnClickListener(loadFileBtnClicked)
        saveFileBtn.setOnClickListener(saveFileBtnClicked)
    }

    private val loadFileBtnClicked = View.OnClickListener {
        val br: BufferedReader
        var fileContent: String = ""

        try {
            br = BufferedReader(InputStreamReader(FileInputStream(file1Name), "UTF-8"))
            var currentLine = br.readLine()

            while (currentLine != null){
                fileContent += currentLine + "\n"
                currentLine = br.readLine()
            }
            br.close()
            output.text = fileContent
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val saveFileBtnClicked = View.OnClickListener {
        val bw: BufferedWriter
        try {
            bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file1Name), "UTF-8"))

            var line2write = input.text.toString()
            bw.write(line2write)
            bw.close()
            // output.text = "檔案成功存儲"
        } catch (e: IOException) {
            e.printStackTrace()
            output.text = e.toString()
        }
    }
}