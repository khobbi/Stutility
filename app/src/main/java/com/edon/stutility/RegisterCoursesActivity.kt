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
import com.edon.stutility.adapters.RegisterCourseAdapter
import com.edon.stutility.databases.TimetableDbHandler
import com.edon.stutility.databinding.ActivityRegisterCoursesBinding
import com.edon.stutility.models.Course

class RegisterCoursesActivity : AppCompatActivity() {
    lateinit var bnd: ActivityRegisterCoursesBinding
    private lateinit var adapter: RegisterCourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityRegisterCoursesBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //toolbar
        val tbarCourses = findViewById<Toolbar>(R.id.tbarCourses)
        setSupportActionBar(tbarCourses)
        val actionBar = supportActionBar
        actionBar?.elevation = 4f
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val dataSet = getCourseNames()
        adapter = RegisterCourseAdapter(this, dataSet)
        bnd.recCourseNames.layoutManager = LinearLayoutManager(this)
        bnd.recCourseNames.adapter = adapter

        //to add the registered course to the recycler view
        bnd.btnAddCourseNameToList.setOnClickListener {
            val courseName = bnd.edtCourseNameField.text.toString()
            var courseExists = false

            if(courseName.isNotEmpty()){
                //check if course already exists before saving
                for(i in 0 until dataSet.size){
                    if(courseName == dataSet[i].courseName){
                        courseExists = true
                        break
                    }
                }
                if(!courseExists){
                    adapter.addCourse(Course(0, courseName), "new")
                } else {
                    Toast.makeText(this, "$courseName already exists", Toast.LENGTH_SHORT).show()
                }
            }

            bnd.edtCourseNameField.setText("")
        }
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.course_names_menu, menu)
        return true
    }

    //onclick of menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.actionResetCourses -> {
                //create alert dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Reset Courses")
                builder.setMessage("Are you sure you want to delete all registered courses?")

                builder.setPositiveButton("CONFIRM"){dialogInterface, _ ->
                    //get instance of timetable db
                    val timetableDbHandler = TimetableDbHandler.getInstance(this)
                    timetableDbHandler.truncateCourseNames()
                    //refresh the list didnt work from notifydatasetchanged() so get
                    //current intent, finish it and start it again
                    val intent = intent
                    finish()
                    startActivity(intent)
                    //val dataSet = timetableDbHandler.viewAllCourseNames()
                    //val adapter = RegisterCourseAdapter(this, dataSet)
                    //adapter.clearAll()

                    Toast.makeText(this, "Courses deleted successfully", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                builder.setNegativeButton("CANCEL"){dialogInterface, _ ->
                    dialogInterface.dismiss()
                }

                val alertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                //return this
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCourseNames(): ArrayList<Course>{
        val timetableDb = TimetableDbHandler.getInstance(this)
        return timetableDb.viewAllCourseNames()
    }

    //edit course name
    fun editCourseName(course: Course){
        val intent = Intent(this, EditCourseNameActivity::class.java)
        intent.putExtra("courseId", course.id)
        intent.putExtra("courseName", course.courseName)
        startActivity(intent)
        finish()
    }
}