package com.example.week7_p1_writeread_sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.lang.Exception

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
        MyDBhp.sCreateTableCommand = "CREATE TABLE " + DB_table + "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "sex TEXT," +
                "address TEXT)"

        // 取得上面指定的檔名資料庫，如果該檔名不存在就會自動建立一個資料庫檔案
        MyDB = MyDBhp.writableDatabase
    }

    private val writeDBbuttonClicked = View.OnClickListener {
        val MyDBhp = MyDBHelper(applicationContext, DB_file, null, 1)
        val MyDB: SQLiteDatabase
        MyDB = MyDBhp.writableDatabase

        // Declare a ContentValuess
        val newRow = ContentValues()

        // 將要新增的欄位"name","sex"與"address"，放入ContentValues中
        newRow.put("name", input.text.toString())
        newRow.put("sex", "")
        newRow.put("address", "")

        try {
            // Insert ContentValues into DB Table
            MyDB.insert(DB_table, null, newRow)
        } catch (allException: Exception) {
            output.text = allException.toString()
        }
    }

    private val readDBbuttonClicked = View.OnClickListener {
        val MyDBhp = MyDBHelper(applicationContext, DB_file, null, 1)
        val MyDB: SQLiteDatabase
        MyDB = MyDBhp.writableDatabase

        try {
            val selectResult = MyDB.query(
                true, DB_table, arrayOf("name", "sex", "address"),
                null, null, null, null, null, null
            )

            if (selectResult.count === 0) {
                output.text = "No Data"
                Toast.makeText(this, "資料庫內沒有資料", Toast.LENGTH_LONG).show()
            } else {
                selectResult.moveToFirst();
                output.text =
                    selectResult.getString(0) + "\t" + selectResult.getString(1) + "\t" + selectResult.getString(
                        2
                    )

                while (selectResult.moveToNext()) {
                    output.append(
                        "\n" + selectResult.getString(0) + "\t" + selectResult.getString(1) + "\t" + selectResult.getString(
                            2
                        )
                    )
                }
            }

            selectResult.close()
        } catch (allException: Exception) {
            output.text = allException.toString()
            // https://stackoverflow.com/questions/36760489/how-to-catch-many-exceptions-at-the-same-time-in-kotlin
        }
    }
}