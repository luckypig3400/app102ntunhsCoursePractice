package com.example.week7_teacherexamplecode

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var output: TextView
    lateinit var queryBtn: Button
    lateinit var insertBtn: Button
    lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        output = findViewById(R.id.output)
        queryBtn = findViewById(R.id.querySQLiteButton)
        insertBtn = findViewById(R.id.insertDBbutton)
        updateBtn = findViewById(R.id.updateDBbutton)


        // 步驟2-1. 主程式 (建立資料庫)
        val DB_FILE = "friends.db"
        val DB_TABLE = "friends"
        val MyDB: SQLiteDatabase
        // 建立自訂的 FriendDbHelper 物件
        val friDbHp = MyDBHelper(applicationContext, DB_FILE, null, 1)
        // 設定建立 table 的指令
        friDbHp.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "sex TEXT," +
                "address TEXT)"
        // 取得上面指定的檔名資料庫，如果該檔名不存在就會自動建立一個資料庫檔案
        MyDB = friDbHp.writableDatabase


        val insertRowDataFunction = View.OnClickListener {
            // 步驟2-3. 主程式 (新增記錄)
            // 宣告一ContentValues
            val newRow = ContentValues()
            // 將要新增的欄位"name","sex"與"address"，放入ContentValues中
            newRow.put("name", "吳曉美")
            newRow.put("sex", "女")
            newRow.put("address", "測試地址")
            // 將ContentValues中的資料，放至資料表中
            MyDB.insert(DB_TABLE, null, newRow)
        }
        insertBtn.setOnClickListener(insertRowDataFunction)


        val querySQLiteDBfunction = View.OnClickListener{
            // 步驟2-2. 主程式 (查詢資料表)
            val c = MyDB.query(
                true, DB_TABLE, arrayOf("name", "sex", "address"),
                null, null, null, null, null, null
            )
            if (c.count === 0) {
                output.text = ""
                Toast.makeText(this, "沒有資料", Toast.LENGTH_LONG).show()
            } else {
                c.moveToFirst();
                output.text = c.getString(0) + "\t" + c.getString(1) + "\t" + c.getString(2)
                while (c.moveToNext()) {
                    output.append("\n" + c.getString(0) + "\t" + c.getString(1) + "\t" + c.getString(2))
                }
            }
        }
        queryBtn.setOnClickListener(querySQLiteDBfunction)

        val updateRowDataFunction = View.OnClickListener {
            // 步驟2-4. 主程式 (更新記錄)
            // 宣告一ContentValues
            val updatedRow = ContentValues()
            // 將要新增的欄位"name","sex"與"address"，放入ContentValues中
            updatedRow.put("name", "早安咖啡女孩")
            updatedRow.put("sex", "女")
            updatedRow.put("address", "台北市大安區早安咖啡館")
            // 將ContentValues中的資料，放至資料表中
            MyDB.update(
                DB_TABLE, updatedRow,
                "id='" + 1 + "'", null
            )
        }
        updateBtn.setOnClickListener(updateRowDataFunction)

    }

}