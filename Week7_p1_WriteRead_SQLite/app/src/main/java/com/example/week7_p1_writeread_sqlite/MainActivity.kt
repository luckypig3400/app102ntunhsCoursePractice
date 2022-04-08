package com.example.week7_p1_writeread_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.database.sqlite.SQLiteDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var writeBtn: Button
    private lateinit var readBtn: Button
    private lateinit var output: TextView

    private val DB_file = "sqliteTest.db"
    private val DB_table = "testTable"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        writeBtn = findViewById(R.id.writeDBbutton)
        readBtn = findViewById(R.id.readDBbutton)
        output = findViewById(R.id.output)

        writeBtn.setOnClickListener(writeDBbuttonClicked)
        readBtn.setOnClickListener(readDBbuttonClicked)

        val MyDB: SQLiteDatabase

        // customize DB Helper Object
        val MyDBhp = MyDBHelper(applicationContext, DB_file, null, 1)

        // set SQL command for Create Table
        MyDBhp.sCreateTableCommand = "CREATE TABLE" + DB_table + "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "sex TEXT," +
                "address TEXT)"

        // 取得上面指定的檔名資料庫，如果該檔名不存在就會自動建立一個資料庫檔案
        MyDB = MyDBhp.writableDatabase
    }

    private val writeDBbuttonClicked = View.OnClickListener {

    }

    private val readDBbuttonClicked = View.OnClickListener {

    }
}