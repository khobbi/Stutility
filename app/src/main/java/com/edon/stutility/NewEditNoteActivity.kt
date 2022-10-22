package com.edon.stutility

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.edon.stutility.databases.NoteDbHandler
import com.edon.stutility.databinding.ActivityNewEditNoteBinding
import com.edon.stutility.models.Note
import java.util.Date
import java.text.SimpleDateFormat

class NewEditNoteActivity : AppCompatActivity() {
    lateinit var bnd: ActivityNewEditNoteBinding
    var noteId: Int = 0
    var typeOfOperation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityNewEditNoteBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //accept the incoming intent and its data coming in
        val noteIntent = intent
        //to hold value for being update of note or creating new note
        typeOfOperation = noteIntent.getStringExtra("type").toString()

        if(typeOfOperation == "update"){
            //put the title and description of the note from the db into the EditTexts
            bnd.edtNoteTitle.setText(intent.getStringExtra("noteTitle").toString())
            bnd.edtNoteDescription.setText(intent.getStringExtra("noteDescription").toString())
            noteId = intent.getIntExtra("noteId", -1)
        }

        bnd.fabSaveUpdate.setOnClickListener { view ->
            saveNote(view, typeOfOperation)
        }
    }

    private fun saveNote(view: View?, typeOfOp: String){
        var noteTitle = bnd.edtNoteTitle.text.toString()
        var noteDescription = bnd.edtNoteDescription.text.toString()
        //set default title and description
        if(noteTitle.isEmpty())
            noteTitle = "[TITLE]"
        if(noteDescription.isEmpty())
            noteDescription = "---"

        val dbhandler = NoteDbHandler.getInstance(this) //object of the database handler

        if(noteTitle.isNotEmpty() || noteDescription.isNotEmpty()){
            var status: Long = 0
            if(typeOfOp == "update") {
                //generate time stamp too in a simple date format
                status = dbhandler.editNote(Note(noteId, noteTitle, noteDescription, "Updated: ${getCurrentDate()}")).toLong()
            } else {
                //passing 0 to the id doesn't matter cos it will be changed automatically
                status = dbhandler.newNote(Note(0, noteTitle, noteDescription, "Created: ${getCurrentDate()}"))
            }
            if(status > -1){
                Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_SHORT).show()

                //start the previous page
                startActivity(Intent(applicationContext, NotesActivity::class.java))
                finish()
            }
        } else {
            Toast.makeText(applicationContext, "Cannot save empty note", Toast.LENGTH_SHORT).show()
        }
    }

    //get the current date used in adding or editing note
    private fun getCurrentDate(): String{
        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
        return sdf.format(Date())
    }

    //confirm on exit without save
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Save")
        builder.setMessage("Note not saved...")

        builder.setPositiveButton("Save"){dialogInterface, _ ->
            saveNote(null, typeOfOperation)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Exit"){ _, _ ->
            startActivity(Intent(applicationContext, NotesActivity::class.java))
            finish()
        }
        builder.setNeutralButton("Cancel"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}