package com.edon.stutility.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.R
import com.edon.stutility.TodoActivity
import com.edon.stutility.models.Todo

class TodoAdapter(val context: Context, val dataSet: ArrayList<Todo>):
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val message: TextView = itemView.findViewById(R.id.tvTodoMessage)
            val priorityColor: View = itemView.findViewById(R.id.priorityColor)
            val done: CheckBox = itemView.findViewById(R.id.chckDone)
            val imgTodoOptions:ImageView = itemView.findViewById(R.id.imgTodoOptions)
            val container: View = itemView.findViewById(R.id.todoContainer)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_todo_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = dataSet[position].message

        //bind and onclick of the options
        holder.imgTodoOptions.setOnClickListener {
            editAndDeletePopupMenu(dataSet[position], position, it)
        }
        holder.container.setOnLongClickListener {
            editAndDeletePopupMenu(dataSet[position], position, it)
            false
        }

        when(dataSet[position].priority){
            1 -> {
                holder.priorityColor.setBackgroundResource(R.drawable.priority_high)
                holder.container.setBackgroundColor(Color.parseColor("#FBE9E7"))
            }
            2 -> {
                holder.priorityColor.setBackgroundResource(R.drawable.priority_moderate)
                holder.container.setBackgroundColor(Color.parseColor("#FBF6E8"))
            }
            3 -> {
                holder.priorityColor.setBackgroundResource(R.drawable.priority_low)
                holder.container.setBackgroundColor(Color.parseColor("#F4F8E7"))
            }
        }

        if(dataSet[position].done == 1) {
            holder.message.paintFlags = holder.message.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.message.paintFlags =
                holder.message.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.done.isChecked = dataSet[position].done == 1 // same as using if and else-if statements
        holder.done.setOnCheckedChangeListener { _, isChecked ->
            if(context is TodoActivity){
                context.doneAndUndone(dataSet[position], isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    //for deleting todos
    private fun deleteTodo(todo: Todo, position: Int){
        if(context is TodoActivity){
            context.deleteTodo(todo)
        }
        notifyItemRemoved(position)
    }

    private fun editTodo(todo: Todo){
        if(context is TodoActivity){
            context.editTodo(todo)
        }
    }

    private fun editAndDeletePopupMenu(todo: Todo, position: Int, view: View){
        val popupMenu = PopupMenu(context, view)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.optTodoEdit -> {
                    editTodo(todo)
                    true
                }
                R.id.optTodoDelete -> {
                    deleteTodo(todo, position)
                    true
                }
                else -> false
            }
        }
        popupMenu.inflate(R.menu.todo_options)
        popupMenu.show()
    }
}