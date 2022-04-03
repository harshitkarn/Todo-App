package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.security.AccessController.getContext


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var title = arrayListOf<String>()
    private var initial = arrayListOf<Int>()
    private var final = arrayListOf<Int>()
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var outerLayout: ConstraintLayout = itemView.findViewById(R.id.outerLayout)
        var todoTitle: TextView = itemView.findViewById(R.id.todoTitle);
        var todoStatus: TextView = itemView.findViewById(R.id.todoStatus);
        var todoProgress: ProgressBar = itemView.findViewById(R.id.todo_progress)
        var todoContainer: LinearLayout = itemView.findViewById(R.id.todo_container);
        var todoButtons: LinearLayout = itemView.findViewById(R.id.todo_btn);
        var cardView: CardView = itemView.findViewById(R.id.cardView);
        var subtractButton:ImageButton =itemView.findViewById(R.id.subtractBtn)
        var addButton:ImageButton =itemView.findViewById(R.id.addBtn)
        var deleteButton:ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false);
        return ViewHolder(v);
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val contextBtn = holder.todoButtons.context
        val db = DBHelper(contextBtn, null)
//        val cursor = db.getTodoItems()
//        cursor!!.moveToFirst()
        holder.todoTitle.text = title[position];
        holder.todoStatus.text = initial[position].toString()+'/'+final[position];
        holder.todoProgress.max = final[position]
        holder.todoProgress.progress = initial[position]
        holder.todoProgress.progressDrawable.setColorFilter(
            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        holder.outerLayout.setOnClickListener {
            holder.todoButtons.visibility =
                if (holder.todoButtons.visibility == View.VISIBLE) View.GONE else View.VISIBLE;
        }
        holder.subtractButton.setOnClickListener {
            if(holder.todoProgress.progress>0) {
                initial[position]-=1
                holder.todoProgress.progress = initial[position]
                holder.todoStatus.text = initial[position].toString()+'/'+final[position];
                db.updateProgress(title[position], initial[position])
            }
        }
        holder.addButton.setOnClickListener {
            if(holder.todoProgress.progress<final[position]) {
                initial[position]+=1
                holder.todoProgress.progress = initial[position]
                holder.todoStatus.text = initial[position].toString()+'/'+final[position];
                db.updateProgress(title[position], initial[position])
            }
        }
        holder.deleteButton.setOnClickListener {
            if(db.deleteTodoItem(title[position])) {
                holder.outerLayout.visibility = View.GONE
                Toast.makeText(contextBtn, "Task \"${title[position]}\" deleted", Toast.LENGTH_SHORT).show()
            }
            else{
                print("Error deleting todo")
            }
        }
    }

    override fun getItemCount(): Int {
        return title.size;
    }
    fun setAdapter(titleTodo: ArrayList<String>, initialVal: ArrayList<Int>, finalVal: ArrayList<Int>){
        title = titleTodo
        initial = initialVal
        final = finalVal
    }
}