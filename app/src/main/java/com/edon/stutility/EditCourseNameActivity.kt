package com.edon.stutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edon.stutility.adapters.RegisterCourseAdapter
import com.edon.stutility.databinding.ActivityEditCourseNameBinding
import com.edon.stutility.models.Course

class EditCourseNameActivity : AppCompatActivity() {
    lateinit var bnd: ActivityEditCourseNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityEditCourseNameBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        // get information from the caller of the intent
        val gottenIntent = intent
        val id = gottenIntent.getIntExtra("courseId", 0)
        val oldName = gottenIntent.getStringExtra("courseName")

        //set edit text with oldname gotten from intent
        bnd.edtNewCourseName.setText(oldName)

        //on clicking on save button
        bnd.btnSave.setOnClickListener {
            if(bnd.edtNewCourseName.text.toString().isNotEmpty()){
                //create adapter passing empty arraylist to it
                val adapter = RegisterCourseAdapter(this, ArrayList())
                //call the addCourse fun in the addapter and passsing old id and new text from the edit text
                adapter.addCourse(Course(id, bnd.edtNewCourseName.text.toString()), "edit")
            }
            startActivity(Intent(this, RegisterCoursesActivity::class.java))
            finish()
        }
    }
}