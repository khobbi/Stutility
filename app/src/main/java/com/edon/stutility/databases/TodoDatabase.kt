package com.edon.stutility.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.edon.stutility.models.Todo

class TodoDatabase private constructor(context: Context):
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        //static instance of this
        private var instance: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            if(instance == null){
                instance = TodoDatabase(context)
            }
            return instance!!
        }

        private const val DB_NAME = "todo_db"
        private const val DB_VERSION = 1

        private const val TABLE_TODO = "todo"
        private const val TD_COL_ID = "_id"
        private const val TD_COL_MESSAGE = "message"
        private const val TD_COL_PRIORITY = "priority"
        private const val TD_COL_DONE = "done"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable  = "CREATE TABLE " + TABLE_TODO + "(" +
                TD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TD_COL_MESSAGE + " TEXT," +
                TD_COL_PRIORITY + " INTEGER," +
                TD_COL_DONE + " INTEGER)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO)
        onCreate(db)
    }

    fun getTodos(): ArrayList<Todo>{
        val todoList = ArrayList<Todo>()
        val db = this.readableDatabase

        val selectQuery = "SELECT * FROM $TABLE_TODO"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (exception: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var todoId: Int; var message:String; var priority: Int; var done: Int
        if(cursor.moveToFirst()){
            do{
                todoId = cursor.getInt(cursor.getColumnIndex(TD_COL_ID))
                message = cursor.getString(cursor.getColumnIndex(TD_COL_MESSAGE))
                priority = cursor.getInt(cursor.getColumnIndex(TD_COL_PRIORITY))
                done = cursor.getInt(cursor.getColumnIndex(TD_COL_DONE))

                todoList.add(Todo(todoId, message, priority, done))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return todoList
    }

    fun addTodo(todo: Todo){
        val db = this. writableDatabase
        val values: ContentValues = ContentValues()
        values.put(TD_COL_MESSAGE, todo.message)
        values.put(TD_COL_PRIORITY, todo.priority)
        values.put(TD_COL_DONE, todo.done)

        db.insert(TABLE_TODO, null, values)

        db.close()
    }

    fun updateTodo(todo: Todo){
        val db = this. writableDatabase
        val values: ContentValues = ContentValues()
        values.put(TD_COL_MESSAGE, todo.message)
        values.put(TD_COL_PRIORITY, todo.priority)
        values.put(TD_COL_DONE, todo.done)

        db.update(TABLE_TODO, values, TD_COL_ID + "=" + todo.id, null)

        db.close()
    }

    fun deleteTodo(todo: Todo){
        val db = this.writableDatabase
        val values: ContentValues = ContentValues()
        values.put(TD_COL_ID, todo.id)

        db.delete(TABLE_TODO, TD_COL_ID + "=" + todo.id, null)

        db.close()
    }

}