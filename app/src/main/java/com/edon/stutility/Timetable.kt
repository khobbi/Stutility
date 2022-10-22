package com.edon.stutility

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.stutility.adapters.DayWithCoursesAdapter
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.databinding.ActivityTimetableBinding
import com.edon.stutility.models.DayOfLecture
import com.edon.stutility.models.Schedule

class Timetable : AppCompatActivity() {
    lateinit var bnd: ActivityTimetableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityTimetableBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //prepare toolbar
        val tbarTimetable = findViewById<Toolbar>(R.id.tbarTimetable)
        setSupportActionBar(tbarTimetable)
        val supportActionBar = supportActionBar
        supportActionBar?.elevation = 4f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //inflate the recycler view
        val dataSet = getDataFromDb()

        val adapter = DayWithCoursesAdapter(this, dataSet)
        bnd.recMainRecForDayOfLecture.layoutManager = LinearLayoutManager(this)
        bnd.recMainRecForDayOfLecture.adapter = adapter

        //onclick for registering new course
        bnd.btnRegisterCourse.setOnClickListener {
            startActivity(Intent(this, RegisterCoursesActivity::class.java))
        }
        //onclick for creating new schedule
        bnd.btnNewSchedule.setOnClickListener {
            startActivity(Intent(this, NewScheduleActivity::class.java))
            finish()
        }
    }

    //oncreate of menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timetable_menu, menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }

    //selecting which menu item is clicked or selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.actionReset -> {
                //create alert dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Reset Timetable")
                builder.setMessage("Are you sure you want to delete all schedules?")

                builder.setPositiveButton("CONFIRM"){dialogInterface, _ ->
                    //get instance of timetable db
                    val timetableDbHandler = TimetableDbHandler.getInstance(this)
                    timetableDbHandler.truncateTimetable()
                    //refresh the list didnt work from notifydatasetchanged() so get
                    //current intent, finish it and start it again
                    val intent = intent
                    finish()
                    startActivity(intent)

                    Toast.makeText(this, "Schedules deleted successfully", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                builder.setNegativeButton("CANCEL"){dialogInterface, _ ->
                    dialogInterface.dismiss()
                }

                val alertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

                true
            }
            R.id.actionHelp -> {
                startActivity(Intent(this, TimetableHelpActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //to prepare the data to be displayed
    private fun getDataFromDb(): ArrayList<DayOfLecture> {
       val timetableDbHandler = TimetableDbHandler.getInstance(this)

        //fix courses into days
        val coursesForMon = timetableDbHandler.viewAllCoursesForADay("monday")
        val coursesForTue = timetableDbHandler.viewAllCoursesForADay("tuesday")
        val coursesForWed = timetableDbHandler.viewAllCoursesForADay("wednesday")
        val coursesForThu = timetableDbHandler.viewAllCoursesForADay("thursday")
        val coursesForFri = timetableDbHandler.viewAllCoursesForADay("friday")
        val coursesForSat = timetableDbHandler.viewAllCoursesForADay("saturday")
        val coursesForSun = timetableDbHandler.viewAllCoursesForADay("sunday")

        //fix all of them into one big array
        val allDaysWithCourses = ArrayList<DayOfLecture>()
        allDaysWithCourses.add(DayOfLecture("Monday", coursesForMon))
        allDaysWithCourses.add(DayOfLecture("Tuesday", coursesForTue))
        allDaysWithCourses.add(DayOfLecture("Wednesday", coursesForWed))
        allDaysWithCourses.add(DayOfLecture("Thursday", coursesForThu))
        allDaysWithCourses.add(DayOfLecture("Friday", coursesForFri))
        allDaysWithCourses.add(DayOfLecture("Saturday", coursesForSat))
        allDaysWithCourses.add(DayOfLecture("Sunday", coursesForSun))

        return allDaysWithCourses
    }

    fun editSchedule(schedule: Schedule) {
        val timetableDbHandler = TimetableDbHandler.getInstance(this)
        val courseName = timetableDbHandler.getCourseName(schedule.courseId)
        //create intent and put necessary Information into it
        //basically, passing the entire lecture to the edit page
        val intent = Intent(this, NewScheduleActivity::class.java)
        intent.putExtra("scheduleId", schedule.scheduleId)
        intent.putExtra("courseName", courseName)
        intent.putExtra("classroom", schedule.classroom)
        intent.putExtra("lecturer", schedule.lecturer)
        intent.putExtra("timeStart", schedule.timeStart)
        intent.putExtra("timeEnd", schedule.timeEnd)
        intent.putExtra("dayOfLecture", schedule.dayOfSchedule)
        intent.putExtra("scheduleType", schedule.type)
        intent.putExtra("type", "update")
        intent.putExtra("courseId", schedule.courseId)
        startActivity(intent)
        finish()
    }

    //to handle previewing of lecture/schedules
    fun previewSchedule(schedule: Schedule){
        val intent = Intent(this@Timetable, SchedulePreviewActivity::class.java)
        intent.putExtra("courseId", schedule.courseId)
        intent.putExtra("classroom", schedule.classroom)
        intent.putExtra("lecturer", schedule.lecturer)
        intent.putExtra("timeStart", schedule.timeStart)
        intent.putExtra("timeEnd", schedule.timeEnd)
        intent.putExtra("type", schedule.type)
        startActivity(intent)
    }
}