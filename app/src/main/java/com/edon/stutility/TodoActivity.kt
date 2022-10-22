package com.edon.stutility

import com.edon.stutility.databases.TodoDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.stutility.adapters.TodoAdapter
import com.edon.stutility.databinding.ActivityTodoBinding
import com.edon.stutility.models.Todo
import com.google.android.material.button.MaterialButton

class TodoActivity : AppCompatActivity() {
    lateinit var bnd: ActivityTodoBinding
    var type: String = "new"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        prepareRecyclerView()

        //fab onclick
        bnd.fabNewTodo.setOnClickListener {
            showCustomSaveAndEditDialog(type, Todo(0, "", 2))
        }
    }

    //prepare Recycler
    private fun prepareRecyclerView(){
        val todoDb = TodoDatabase.getInstance(this)
        val dataSet = todoDb.getTodos()

        if(dataSet.size == 0){
            bnd.recTodoList.visibility = View.GONE
            bnd.cardTodoDone.visibility = View.GONE
            bnd.progDoneTodos.visibility = View.GONE
            bnd.tvTodoDoneProgText.visibility = View.GONE
            bnd.imgEmptyTodoList.visibility = View.VISIBLE
        } else {
            bnd.recTodoList.visibility = View.VISIBLE
            bnd.cardTodoDone.visibility = View.VISIBLE
            bnd.progDoneTodos.visibility = View.VISIBLE
            bnd.tvTodoDoneProgText.visibility = View.VISIBLE
            bnd.imgEmptyTodoList.visibility = View.GONE
            val adapter = TodoAdapter(this, dataSet)
            bnd.recTodoList.layoutManager = LinearLayoutManager(this)
            bnd.recTodoList.adapter = adapter

            updateDoneTodosView(dataSet)
        }
    }

    fun updateDoneTodosView(dataSet: ArrayList<Todo>){
        bnd.progDoneTodos.max = dataSet.size
        var doneTodos = 0
        //count the done todos
        for(todo in dataSet){
            if(todo.done == 1){
                doneTodos++
            }
        }
        bnd.progDoneTodos.progress = doneTodos

        //show the number of done todos
        bnd.tvTodoDoneProgText.text = "$doneTodos/${dataSet.size}"
    }

    //show dialog when editing or adding new
    private fun showCustomSaveAndEditDialog(typeOfOp: String, todo: Todo){
        val alertDialog = AlertDialog.Builder(this)
        val customView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_todo_new_and_edit, null)

        //setting view variables
        val dialogTitle: TextView = customView.findViewById(R.id.tvTodoDialogTitle)
        val edtTodoMessage: EditText = customView.findViewById(R.id.edtTodoDialogMessage)
        val spnTodoPriority: Spinner = customView.findViewById(R.id.spnTodoPriorities)
        val btnDiscardDialog: MaterialButton = customView.findViewById(R.id.btnTodoDialogDiscard)
        val btnDialogSave: MaterialButton = customView.findViewById(R.id.btnTodoDialogSave)

        if(typeOfOp == "new"){
            dialogTitle.text = "New Todo"
            spnTodoPriority.setSelection(1)
        } else if(typeOfOp == "update"){
            dialogTitle.text = "Update Todo"
            edtTodoMessage.setText(todo.message)
            when(todo.priority){
                1 -> {
                    spnTodoPriority.setSelection(0)
                }
                2 -> {
                    spnTodoPriority.setSelection(1)
                }
                3 -> {
                    spnTodoPriority.setSelection(2)
                }
            }
        }
        alertDialog.setView(customView)

        val customDialog = alertDialog.create()
        customDialog.show()

        btnDiscardDialog.setOnClickListener {
            customDialog.cancel()
        }

        btnDialogSave.setOnClickListener {
            if (edtTodoMessage.text.isNotEmpty()) {
                if(typeOfOp == "new")
                    saveTodo(typeOfOp,  edtTodoMessage, spnTodoPriority)
                else
                    saveTodo(typeOfOp,  edtTodoMessage, spnTodoPriority, todo.id, todo.done)
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
                prepareRecyclerView()
            } else {
                Toast.makeText(this, "Todo cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //save to-do
    private fun saveTodo(typeOp: String, edtMessage: EditText, spnPriority: Spinner, oldId: Int = 0, oldDone: Int = 0) {
        val todoDb = TodoDatabase.getInstance(this)

        if (typeOp == "new") {
            val message = edtMessage.text.toString()
            val priority = spnPriority.selectedItemPosition + 1 // to shift it from 0
            todoDb.addTodo(Todo(0, message, priority))
        } else if(typeOp == "update") {
            val message = edtMessage.text.toString()
            val priority = spnPriority.selectedItemPosition + 1 // to shift it from 0
            todoDb.updateTodo(Todo(oldId, message, priority, oldDone))
            type = "new" //reset the type
        }
    }

    //for handling delete of todos
    fun deleteTodo(todo: Todo){
        val todoDb = TodoDatabase.getInstance(this)
        todoDb.deleteTodo(todo)
        prepareRecyclerView()
    }

    fun editTodo(todo: Todo) {
        type = "update"
        showCustomSaveAndEditDialog(type, todo)
    }

    //done and undone of todos
    fun doneAndUndone(todo: Todo, checked: Boolean) {
        val todo = todo
        val todoDb = TodoDatabase.getInstance(this)
        todo.done = if (checked) 1 else 0
        todoDb.updateTodo(todo)
        prepareRecyclerView()
    }
}