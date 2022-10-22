package com.edon.stutility.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.edon.stutility.models.Note

class NoteDbHandler private constructor(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //companion object containing static variables
    companion object{
        //making this a singleton
        private var instance: NoteDbHandler? = null

        //use this to get a single instance of the class
        fun getInstance(context: Context): NoteDbHandler{
            if(instance == null){
                instance = NoteDbHandler(context)
            }
            return instance!!
        }

        //static variables
        private const val DATABASE_NAME = "NotesDatabase"
        private const val DATABASE_VERSION = 2

        private const val DB_TABLE = "notestable"
        private const val COL_ID =  "_id"
        private const val COL_TITLE = "title"
        private const val COL_DESC = "description"
        private const val COL_UPDATETIME = "lastupdate"
    }

    //basically executes SQLite CREATE command
    override fun onCreate(db: SQLiteDatabase?){
        //variable to hold the create statement
        //CREATE TABLE notestable(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, lastupddate TEXT)
        val CREATE_NOTE_TABLE = "CREATE TABLE " + DB_TABLE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT," +
                COL_DESC + " TEXT," +
                COL_UPDATETIME + " TEXT" + ");"

        db?.execSQL(CREATE_NOTE_TABLE) //execute the above statement
    }

    //when upgrading the version to new one
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DB_TABLE) //delete the table if it already exists
        onCreate(db) // and execute fresh one
    }

    //CRUD OPERATIONS
    //create new Note record
    fun newNote(note: Note): Long{
        val db = this.writableDatabase //create writable version of the db

        val contentValues = ContentValues() //to contain the values to be put into the table
        //contentValues.put(COL_ID, note.id) will be incremented automatically
        contentValues.put(COL_TITLE, note.title)
        contentValues.put(COL_DESC, note.description)
        contentValues.put(COL_UPDATETIME, note.lastUpdate)

        //insertion into table
        val success = db.insert(DB_TABLE, null, contentValues)

        db.close() //close db connection

        return success //if successful or not (1 or 0)
    }

    //view / read note record
    fun viewNotes(): ArrayList<Note>{
        val noteList: ArrayList<Note> = ArrayList<Note>() // to contain the notes

        val selectQuery = "SELECT * FROM $DB_TABLE" //sqlite query

        val db = this.readableDatabase
        var cursor: Cursor? = null //empty cursor

        //execute the query
        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var note_id: Int; var note_title: String; var note_description: String
        var note_lastUpdate: String

        //step through cursor, adding each record(note) to array list
        if(cursor.moveToFirst()){
            do {
                note_id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                note_title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                note_description = cursor.getString(cursor.getColumnIndex(COL_DESC))
                note_lastUpdate = cursor.getString(cursor.getColumnIndex(COL_UPDATETIME))

                noteList.add(Note(note_id, note_title, note_description, note_lastUpdate))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return noteList
    }

    //edit /update note record
    fun editNote(note: Note): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, note.title)
        contentValues.put(COL_DESC, note.description)
        contentValues.put(COL_UPDATETIME, note.lastUpdate)

        //update record
        val success = db.update(DB_TABLE, contentValues, COL_ID + "=" + note.id, null)

        db.close()
        return success
    }

    //delete note
    fun deleteNote(note: Note): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID, note.id)

        //deleting
        val success = db.delete(DB_TABLE, COL_ID + "=" + note.id, null)

        db.close()
        return success
    }
}