package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception

class AddTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        try{
            val addButton: Button = findViewById(R.id.todoAdd)
            addButton.setOnClickListener {
                val taskName: EditText = findViewById(R.id.todoTitleET)
                val initialValue: EditText = findViewById(R.id.todoInitialET)
                val finalValue: EditText = findViewById(R.id.todoFinalET)
                val title = taskName.text.toString().trim()
                val initialVal = if(initialValue.text.toString()=="") 0 else initialValue.text.toString().toInt()
                val finalVal = if(finalValue.text.toString()=="") 0 else finalValue.text.toString().toInt()
                if(title.trim()==""){
                    taskName.error = "Task Name Empty"
                }
                else if(initialValue.text.toString()==""){
                    initialValue.error = "Initial value empty"
                }
                else if(finalValue.text.toString()==""){
                    finalValue.error = "Final value empty"
                }
                else if(initialVal>finalVal){
                    initialValue.error = "Initial value cannot be more than final"
                }
                else if(finalVal<1||finalVal>50){
                    finalValue.error = "Please enter in range 1-50"
                }
                else{
                    val db = DBHelper(this, null)
                    if (db.addTodoItem(title, initialVal, finalVal)) {
                        Toast.makeText(
                            applicationContext,
                            "Task Added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else
                        taskName.error = "Task already exists"
                }
            }
            }catch (e: Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT)
            }
    }
}