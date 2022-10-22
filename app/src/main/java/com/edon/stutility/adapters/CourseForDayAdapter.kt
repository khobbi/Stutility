package com.edon.stutility.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.R
import com.edon.stutility.Timetable
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.models.Schedule

class CourseForDayAdapter(private val context: Context, private val dataSet: ArrayList<Schedule>):
    RecyclerView.Adapter<CourseForDayAdapter.ViewHolder>() {
    //view holder describing the child container
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val courseName: TextView = itemView.findViewById(R.id.tvCourseName)
        val classroom: TextView = itemView.findViewById(R.id.tvClassroom)
        val lecturer: TextView = itemView.findViewById(R.id.tvLecturer)
        val startTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val endTime: TextView = itemView.findViewById(R.id.tvEndTime)
        val typeColor: LinearLayout = itemView.findViewById(R.id.scheduleTypeColor)

        val lecture = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_lecture_layout,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timetableDbHandler = TimetableDbHandler.getInstance(context)

        holder.apply {
            //courseName.text = dataSet[position].scheduleName
            courseName.text = timetableDbHandler.getCourseName(dataSet[position].courseId)
            classroom.text = dataSet[position].classroom
            lecturer.text = dataSet[position].lecturer
            startTime.text = dataSet[position].timeStart
            endTime.text = dataSet[position].timeEnd
            if(dataSet[position].type == "lecture"){
                typeColor.setBackgroundResource(R.drawable.rounded_corner_for_timetable)
            } else {
                typeColor.setBackgroundResource(R.drawable.rounded_corner_for_timetable2)
            }
        }

        //onLongClick of Lecture, ask if delete or edit
        holder.lecture.setOnLongClickListener {
            showEditDeleteLecture(dataSet[position], position, dataSet[position].courseId)
            false
        }

        //on click
        holder.lecture.setOnClickListener {
            if(context is Timetable)
                context.previewSchedule(dataSet[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun showEditDeleteLecture(schedule: Schedule, position: Int, courseId: Int) {
        val timetableDbHandler = TimetableDbHandler.getInstance(context)
        //builder of the dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Action")
        builder.setMessage("Editing or Deleting ${timetableDbHandler.getCourseName(courseId)}?")

        //negative button for deleting
        builder.setNegativeButton("Delete"){ dialogInterface, _ ->
            //val timetableDbHandler = TimetableDbHandler.getInstance(context)
            val status = timetableDbHandler.deleteScheduleFromTimetable(schedule)
            if(status > -1){
                Toast.makeText(context, "Lecture deleted successfully.", Toast.LENGTH_SHORT).show()
                dataSet.remove(dataSet[position])
                notifyItemRemoved(position)
            }
            dialogInterface.dismiss()
        }
        //positive button for editing
        builder.setPositiveButton("Edit"){ dialogInterface, _ ->
            //call edit function in the parent context
            if(context is Timetable)
                context.editSchedule(schedule)

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