package com.edon.stutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.stutility.adapters.NoteAdapter
import com.edon.stutility.databases.NoteDbHandler
import com.edon.stutility.databinding.ActivityNotesBinding
import com.edon.stutility.models.Note

class NotesActivity : AppCompatActivity() {
    lateinit var bnd: ActivityNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        val dataSet = getNotesInDatabase()
        //IF NO NOTES IN DB SHOW INFO
        if(dataSet.size > 0){
            bnd.tvIfNotesAreEmpty.visibility = View.GONE
            bnd.recDisplayNotes.visibility = View.VISIBLE

            val adapter = NoteAdapter(this, dataSet)

            bnd.recDisplayNotes.layoutManager = LinearLayoutManager(this)
            bnd.recDisplayNotes.adapter = adapter
        } else {
            bnd.tvIfNotesAreEmpty.visibility = View.VISIBLE
            bnd.recDisplayNotes.visibility = View.GONE

            bnd.tvIfNotesAreEmpty.text = getString(R.string.please_create_new_note)
        }

        //clicking on fab to Create new note
        bnd.fabCreateNewNote.setOnClickListener {
            startActivity(Intent(this, NewEditNoteActivity::class.java))
            finish()
        }
    }

    //for updating note records in the database
    fun editNoteRecord(note: Note) {
        val noteTitle = note.title
        val noteDescription = note.description

        val intent = Intent(this, NewEditNoteActivity::class.java)
        intent.putExtra("type", "update")
        intent.putExtra("noteId", note.id)
        intent.putExtra("noteTitle", noteTitle)
        intent.putExtra("noteDescription", noteDescription)
        startActivity(intent)

        finish()
    }

    //fun for deleting note record in database is in NoteAdapter.kt

    //retrieve all the Notes from db and return it
    private fun getNotesInDatabase(): ArrayList<Note>{
        val dbHandler = NoteDbHandler.getInstance(this)
        return dbHandler.viewNotes()
    }

    fun restartMe() {
        val intent = intent
        finish()
        startActivity(intent)
    }
}