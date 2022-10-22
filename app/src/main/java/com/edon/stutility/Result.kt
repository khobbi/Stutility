package com.edon.stutility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edon.stutility.databinding.ActivityResultBinding

class Result : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //creating intent for receiving the sent one
        val intent = intent

        //creating variables to hold the intents extras
        //creating variables to hold the intents extras
        val grades = intent.getStringArrayExtra("course_grades")
        val remarks = intent.getStringArrayExtra("course_remarks")
        val gpa = intent.getFloatExtra("studentGpa", 0.0f)
        val studentClass = intent.getStringExtra("studentClass")

        //pass the extra information from the intent to the Views
        binding.txtGrade1.text = grades?.get(0)
        binding.txtGrade2.text = grades?.get(1)
        binding.txtGrade3.text = grades?.get(2)
        binding.txtGrade4.text = grades?.get(3)
        binding.txtGrade5.text = grades?.get(4)
        binding.txtGrade6.text = grades?.get(5)
        binding.txtGrade7.text = grades?.get(6)
        binding.txtGrade8.text = grades?.get(7)
        binding.txtGrade9.text = grades?.get(8)
        binding.txtGrade10.text = grades?.get(9)

        binding.txtRemark1.text = remarks?.get(0)
        binding.txtRemark2.text = remarks?.get(1)
        binding.txtRemark3.text = remarks?.get(2)
        binding.txtRemark4.text = remarks?.get(3)
        binding.txtRemark5.text = remarks?.get(4)
        binding.txtRemark6.text = remarks?.get(5)
        binding.txtRemark7.text = remarks?.get(6)
        binding.txtRemark8.text = remarks?.get(7)
        binding.txtRemark9.text = remarks?.get(8)
        binding.txtRemark10.text = remarks?.get(9)

        binding.txtGPA.text = gpa.toString()
        binding.txtClass.text = studentClass
    }
}