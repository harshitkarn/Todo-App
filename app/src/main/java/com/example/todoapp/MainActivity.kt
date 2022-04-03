package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager?= null;
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val title = ArrayList<String>()
        val initial = ArrayList<Int>()
        val final = ArrayList<Int>()
        val db = DBHelper(this, null)
        val cursor = db.getTodoItems()
        if((cursor != null) && (cursor.count > 0)){
            cursor!!.moveToFirst()
            title.add(cursor.getString(0))
            initial.add(cursor.getInt(1))
            final.add(cursor.getInt(2))
            while (cursor.moveToNext()){
                title.add(cursor.getString(0))
                initial.add(cursor.getInt(1))
                final.add(cursor.getInt(2))
            }
        }

        layoutManager = LinearLayoutManager(this);
        val recycleView = findViewById<RecyclerView>(R.id.recyclerView);
        recycleView.layoutManager = layoutManager;
        adapter = RecyclerAdapter();
        (adapter as RecyclerAdapter).setAdapter(title, initial, final)
        recycleView.adapter = adapter;
        val addNewButton = findViewById<ImageButton>(R.id.addNewTask)
        addNewButton.setOnClickListener {
            startActivity(Intent(this, AddTask::class.java))
        }


    }
}