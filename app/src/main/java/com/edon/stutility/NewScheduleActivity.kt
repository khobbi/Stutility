package com.edon.stutility

import android.R
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.databinding.ActivityNewScheduleBinding
import com.edon.stutility.models.Course
import kotlin.collections.ArrayList
import com.edon.stutility.models.Schedule
import java.text.SimpleDateFormat
import java.util.*


class NewScheduleActivity : AppCompatActivity() {
    lateinit var bnd: ActivityNewScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityNewScheduleBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        val courseNames = getCourseNames() //get all the course names
        val dayNames = ArrayList<String>()
        dayNames.add("Monday")
        dayNames.add("Tuesday")
        dayNames.add("Wednesday")
        dayNames.add("Thursday")
        dayNames.add("Friday")
        dayNames.add("Saturday")
        dayNames.add("Sunday")
        val lectureTypes = ArrayList<String>()
        lectureTypes.add("Lecture")
        lectureTypes.add("Personal")
        //create array adapters with the layouts, and arrays
        val arrayAdapterCourseNames = ArrayAdapter<String>(this, R.layout.simple_list_item_1, courseNames)
        val arrayAdapterDayNames = ArrayAdapter<String>(this, R.layout.simple_list_item_1, dayNames)
        val arrayAdapterLectTypes = ArrayAdapter<String>(this, R.layout.simple_list_item_1, lectureTypes)

        //put the array adapter into the spinner
        bnd.spnCourseName.adapter = arrayAdapterCourseNames
        bnd.spnDays.adapter = arrayAdapterDayNames
        bnd.spnTypes.adapter = arrayAdapterLectTypes

        //get gotten intent content
        val gottenIntent = intent
        val intentType = gottenIntent.getStringExtra("type").toString()
        val oldId = gottenIntent.getIntExtra("scheduleId", 0)
        val oldCourseId = gottenIntent.getIntExtra("courseId", 0)
        if(intentType == "update"){
            val oldTimeStarts = gottenIntent.getStringExtra("timeStart").toString()
            val oldTimeEnds = gottenIntent.getStringExtra("timeEnd").toString()
            val oldCourseName = gottenIntent.getStringExtra("courseName").toString()
            val oldDay = gottenIntent.getStringExtra("dayOfLecture").toString()
            val oldScheduleType = gottenIntent.getStringExtra("scheduleType")

            //set the views with the intent values
            bnd.edtClassroom.setText(intent.getStringExtra("classroom").toString())
            bnd.edtLecturer.setText(intent.getStringExtra("lecturer").toString())
            bnd.tvTimeStartsDisplay.text = oldTimeStarts
            bnd.tvTimeEndsDisplay.text = oldTimeEnds
            //compare each row of spinner with the coursename and day from intent
            //and set them accordingly
            for (i in 0 until arrayAdapterCourseNames.count){
                if (bnd.spnCourseName.getItemAtPosition(i).toString() == oldCourseName)
                    bnd.spnCourseName.setSelection(i)
            }
            //for the day and type in spinner, convert it to lowercase and compare cos the one
            // from the intent is in lowercase
            for (i in 0 until arrayAdapterDayNames.count){
                if (bnd.spnDays.getItemAtPosition(i).toString().lowercase() == oldDay)
                    bnd.spnDays.setSelection(i)
            }
            for(i in 0 until arrayAdapterLectTypes.count){
                if(bnd.spnTypes.getItemAtPosition(i).toString().lowercase() == oldScheduleType)
                    bnd.spnTypes.setSelection(i)
            }
        }

        //onclick for Save button
        bnd.btnSaveSchedule.setOnClickListener {
            //pass the type of operation(update or create new), old id for update and number of course names
            saveOrUpdate(intentType, oldId, arrayAdapterCourseNames.count, oldCourseId)
        }

        //onclick for time start textview
        bnd.tvTimeStartsDisplay.setOnClickListener {
            setTime(1)
        }
        //onclick for time end textview
        bnd.tvTimeEndsDisplay.setOnClickListener {
            setTime(2)
        }
    }

    //doing the saving of update and new lectures
    private fun saveOrUpdate(type: String, oldId: Int, numberOfCourseNames: Int, oldCourseId: Int) {
        val sdf = SimpleDateFormat("HH:mm")
        val startTime: Date? = sdf.parse(bnd.tvTimeStartsDisplay.text.toString())
        val endTime: Date? = sdf.parse(bnd.tvTimeEndsDisplay.text.toString())
        //make sure the beginning and end of lecture are not same, start time !> end time and course not empty
        if(bnd.tvTimeEndsDisplay.text.toString() != (bnd.tvTimeStartsDisplay.text.toString()) && startTime!!.time < endTime!!.time &&
            numberOfCourseNames > 0){
            val timetableDbHandler = TimetableDbHandler.getInstance(this)
            var success = 0
            if(type != "update"){
                success = timetableDbHandler.insertIntoTimetable(Schedule(
                    oldId,
                    bnd.edtClassroom.text.toString(),
                    bnd.edtLecturer.text.toString(),
                    bnd.tvTimeStartsDisplay.text.toString(),
                    bnd.tvTimeEndsDisplay.text.toString(),
                    bnd.spnDays.selectedItem.toString().lowercase(),
                    bnd.spnTypes.selectedItem.toString().lowercase(),
                    getSelectedCourseId()
                )).toInt()
            } else{
                success = timetableDbHandler.updateTimetableCourse(Schedule(
                    oldId,
                    bnd.edtClassroom.text.toString(),
                    bnd.edtLecturer.text.toString(),
                    bnd.tvTimeStartsDisplay.text.toString(),
                    bnd.tvTimeEndsDisplay.text.toString(),
                    bnd.spnDays.selectedItem.toString().lowercase(),
                    bnd.spnTypes.selectedItem.toString().lowercase(),
                    oldCourseId
                ))
            }
            if(success > -1) {
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Timetable::class.java))
                finish()
            }
            else {
                Toast.makeText(this, "Error saving to Database", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Lecture name or time error", Toast.LENGTH_SHORT).show()
        }
    }

    //get course names from the Courses table
    private fun getCourseNames(): ArrayList<String>{
        val timetableDbHandler = TimetableDbHandler.getInstance(this)
        //get all the courses with ids
        val listOfCourses = timetableDbHandler.viewAllCourseNames()
        val listOfCourseNames = ArrayList<String>()

        for(i in 0 until listOfCourses.size){
            //get only the names
            listOfCourseNames.add(listOfCourses[i].courseName)
        }
        return listOfCourseNames
    }

    //get course ids from the Courses table
    private fun getCourses(): ArrayList<Course>{
        val timetableDbHandler = TimetableDbHandler.getInstance(this)
        //get all courses
        return timetableDbHandler.viewAllCourseNames()
    }

    private fun getSelectedCourseId(): Int {
        //assuming all course names are different, so compare the names and choose
        //the one which has the same id as the selected one from the spinner
        val courses = getCourses()
        var courseId: Int = 0
        for(i in 0 until courses.size){
            if(courses[i].courseName == bnd.spnCourseName.selectedItem.toString()){
                courseId = courses[i].id
            }
        }
        return courseId
    }

    //get time picker and set times for start and end of lecture
    private fun setTime(whichView: Int){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            if(whichView == 1)
                bnd.tvTimeStartsDisplay.text = SimpleDateFormat("HH:mm").format(cal.time)
            else
                bnd.tvTimeEndsDisplay.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onBackPressed() {
    startActivity(Intent(this, Timetable::class.java))
    finish()
    //super.onBackPressed()
    }
}