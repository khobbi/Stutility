package com.edon.stutility.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.databases.NoteDbHandler
import com.edon.stutility.NotesActivity
import com.edon.stutility.R
import com.edon.stutility.models.Note

class NoteAdapter(private val context: Context, private val dataSet: ArrayList<Note>):
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
        //ViewHolder describing the custom note layout
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val noteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
            val noteDescription: TextView = itemView.findViewById(R.id.tvNoteDescription)
            val noteLastEdit: TextView = itemView.findViewById(R.id.tvTimestamp)
            val imgDeleteNote: ImageView = itemView.findViewById(R.id.imgDeleteNote)

            val entireNote: CardView = itemView.findViewById(R.id.cardNote)
        }

    //inflating the container (custom layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_layout_for_note,
            parent,
            false
        )
        return ViewHolder(view)
    }

    //binding views with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //using the apply in order not to use holder. all the time
        holder.apply {
            noteTitle.text = dataSet[position].title
            noteDescription.text = dataSet[position].description
            noteLastEdit.text = dataSet[position].lastUpdate
            imgDeleteNote.setImageResource(R.drawable.trash_can)

            //updating the background color according to odd and even numbers of position
            if(position % 2 != 0){
                entireNote.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.white)
                )
            } else {
                entireNote.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.noteback_yellow)
                )
            }

            //clicking on entire note should edit/update the note
            entireNote.setOnClickListener {
                //call editNoteRecord fun in the NotesActivity
                if(context is NotesActivity){
                    context.editNoteRecord(dataSet[position])
                }
            }

            //for deleting note
            imgDeleteNote.setOnClickListener {
                //call showDeleteNoteDialog
                showDeleteNoteDialog(dataSet[position], position)
            }
        }
    }

    //get size of dataset
    override fun getItemCount(): Int {
        return dataSet.size
    }

    //for deleting note record in database
    private fun showDeleteNoteDialog(note: Note, position: Int){
        //builder of the dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Note")
        builder.setMessage("Are you sure you want to delete ${note.title}?")

        //positive button
        builder.setPositiveButton("Yes"){ dialogInterface, _ ->
            val dbHandler = NoteDbHandler.getInstance(context)
            val status = dbHandler.deleteNote(note)
            if(status > -1){
                Toast.makeText(context, "Note deleted successfully.", Toast.LENGTH_SHORT).show()
                //dataSet.remove(dataSet[position])
                dataSet.removeAt(position)
                notifyItemRemoved(position)
                //TODO: FIND BETTER WAY OF REFRESHING THE LIST INSTEAD OF RESTARTING IT
                if(context is NotesActivity)
                    context.restartMe()
            }
            dialogInterface.dismiss()
        }
        //negative button
        builder.setNegativeButton("Cancel"){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        //create the dialog
        val alertDialog = builder.create()
        //set other properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}