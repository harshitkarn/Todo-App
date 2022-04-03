package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "todo.db", factory, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE todoItem(title TEXT PRIMARY KEY,initialVal INTEGER,finalVal INTEGER)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS todoItem")
        onCreate(db)
    }

    fun addTodoItem(todoTitle : String, initialValue : Int, finalValue : Int): Boolean {

        val values = ContentValues()
        values.put("title", todoTitle)
        values.put("initialVal", initialValue)
        values.put("finalVal", finalValue)

        val db = this.writableDatabase
        val result = db.insert("todoItem", null, values)
        db.close()
        return result >= 0
    }

    fun getTodoItems(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM todoItem", null)

    }

    fun deleteTodoItem(title : String):Boolean {
        val db = this.writableDatabase
        return db.delete("todoItem", "title='$title'", null)>0
    }

    fun updateProgress(title: String,initialValue: Int){
        val db = this.writableDatabase
        db.execSQL("UPDATE todoItem SET initialVal='$initialValue' WHERE title='$title'")
    }
}