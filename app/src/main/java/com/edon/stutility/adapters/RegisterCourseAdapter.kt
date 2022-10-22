package com.edon.stutility.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.R
import com.edon.stutility.RegisterCoursesActivity
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.models.Course

class RegisterCourseAdapter(private val context: Context, private val dataSet: ArrayList<Course>):
    RecyclerView.Adapter<RegisterCourseAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val courseName: TextView = itemView.findViewById(R.id.tvCourseNameViewer)

        val wholeView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_course_name_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.courseName.text = dataSet[position].courseName

        //onLongClick of Course name, ask if delete or edit
        holder.wholeView.setOnLongClickListener {
            showEditAndDeleteDialog(dataSet[position], position)

            false
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    //for adding and updating depending on the type gotten
    fun addCourse(course: Course, type: String){
        val timetabeDb = TimetableDbHandler.getInstance(context)
        if(type == "new"){
            dataSet.add(course)
            //insert data to the last position
            notifyItemInserted(dataSet.size - 1)
            //insert into table with data at last index of subjects array
            timetabeDb.populateTableCourses(course)
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
        } else{
            timetabeDb.updateCourseName(course)
            notifyDataSetChanged()
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
        }

    }

    //for handling edit and deletion of course names
    private fun showEditAndDeleteDialog(course: Course, position: Int) {
        //builder of the dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Action")
        builder.setMessage("Editing or Deleting ${course.courseName}?")

        //negative button for deleting
        builder.setNegativeButton("Delete"){ dialogInterface, _ ->
            val timetableDbHandler = TimetableDbHandler.getInstance(context)
            val status = timetableDbHandler.deleteCourseName(course)
            if(status > -1){
                Toast.makeText(context, "Course deleted successfully.", Toast.LENGTH_SHORT).show()
                dataSet.remove(dataSet[position])
                notifyItemRemoved(position)
            }
            dialogInterface.dismiss()
        }
        //positive button for editing
        builder.setPositiveButton("Edit"){ dialogInterface, _ ->
            //call edit function in the parent context
            if(context is RegisterCoursesActivity)
                context.editCourseName(course)

            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Cancel"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        //create the dialog
        val alertDialog = builder.create()
        //set other properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}