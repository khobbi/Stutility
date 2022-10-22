package com.edon.stutility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.databinding.ActivitySchedulePreviewBinding


class SchedulePreviewActivity : AppCompatActivity() {
    lateinit var bnd: ActivitySchedulePreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivitySchedulePreviewBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //get intent
        val in_intent = intent
        val courseId = in_intent.getIntExtra("courseId", 0).toInt()
        val lecturer = in_intent.getStringExtra("lecturer").toString()
        val timeStart = in_intent.getStringExtra("timeStart").toString()
        val timeEnd = in_intent.getStringExtra("timeEnd").toString()
        val type = in_intent.getStringExtra("type").toString()
        val classroom = in_intent.getStringExtra("classroom").toString()

        //set values into Views
        if(type == "lecture"){
            bnd.llTypeColor.setBackgroundResource(R.drawable.rounded_corner_for_timetable)
        } else {
            bnd.llTypeColor.setBackgroundResource(R.drawable.rounded_corner_for_timetable2)
        }
        bnd.llTypeColor.elevation = 3f

        val timetableDbHandler = TimetableDbHandler.getInstance(this)
        bnd.tvCName.text = timetableDbHandler.getCourseName(courseId)
        bnd.tvTStart.text = timeStart
        bnd.tvTEnd.text = timeEnd
        bnd.tvClassroomV.text = classroom
        bnd.tvLecturerV.text = lecturer
        bnd.tvType.text = type
    }
}