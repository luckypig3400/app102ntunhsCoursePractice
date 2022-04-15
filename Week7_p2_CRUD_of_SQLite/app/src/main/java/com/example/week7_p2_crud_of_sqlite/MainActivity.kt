package com.example.week7_p2_crud_of_sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var idInput: EditText
    lateinit var nameInput: EditText
    lateinit var output: TextView
    lateinit var insertBtn: Button
    lateinit var updateBtn: Button
    lateinit var deleteBtn: Button
    lateinit var readBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idInput = findViewById(R.id.idInput)
        nameInput = findViewById(R.id.nameInput)
        output = findViewById(R.id.output)
        insertBtn = findViewById(R.id.insertButton)
        updateBtn = findViewById(R.id.updateButton)
        deleteBtn = findViewById(R.id.deleteButton)
        readBtn = findViewById(R.id.readDataButton)

        // Create Database
        val DB_FILE = "friends.db"
        val DB_TABLE = "friendList"
        val MyDB: SQLiteDatabase
        // 建立自訂的 FriendDbHelper 物件
        val friDbHp = MyDBHelper(applicationContext, DB_FILE, null, 1)
        // 設定建立 table 的指令
        friDbHp.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL)"
        // 取得上面指定的檔名資料庫，如果該檔名不存在就會自動建立一個資料庫檔案
        MyDB = friDbHp.writableDatabase


        // Insert Data to DB function
        val insertRowDataFunction = View.OnClickListener {
            // 宣告一ContentValues
            val newRow = ContentValues()
            try {
                newRow.put("id", idInput.text.toString().toInt())
                newRow.put("name", nameInput.text.toString())
                // 將ContentValues中的資料，放至資料表中
                MyDB.insert(DB_TABLE, null, newRow)
            }catch (allException: Exception){
                Toast.makeText(this, allException.toString(), Toast.LENGTH_LONG).show()
            }
        }
        insertBtn.setOnClickListener(insertRowDataFunction)


        // Read DB data function
        val querySQLiteDBfunction = View.OnClickListener {
            val c = MyDB.query(
                true, DB_TABLE, arrayOf("id", "name"),
                null, null, null, null, null, null
            )
            if (c.count === 0) {
                output.text = ""
                Toast.makeText(this, "沒有資料", Toast.LENGTH_LONG).show()
            } else {
                c.moveToFirst();
                output.text = c.getString(0) + ":\t" + c.getString(1)
                while (c.moveToNext()) {
                    output.append(
                        "\n" + c.getString(0) + ":\t" + c.getString(1)
                    )
                }
            }
        }
        readBtn.setOnClickListener(querySQLiteDBfunction)
    }
}