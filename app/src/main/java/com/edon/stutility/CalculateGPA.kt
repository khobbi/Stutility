package com.edon.stutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.edon.stutility.databinding.ActivityCalculateGpaBinding

class CalculateGPA : AppCompatActivity() {
    lateinit var binding: ActivityCalculateGpaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateGpaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //method for the Calculate button
    fun onClickCalculate(view: View?) {
        //reference the EditText and Spinners
        //all done in the binding file

        //if any of the inputs is not empty, do calculations else don't
        if(binding.edtAss1.text.isNotEmpty() ||
            binding.edtAss2.text.isNotEmpty() ||
            binding.edtAss3.text.isNotEmpty() ||
            binding.edtAss4.text.isNotEmpty() ||
            binding.edtAss5.text.isNotEmpty() ||
            binding.edtAss6.text.isNotEmpty() ||
            binding.edtAss7.text.isNotEmpty() ||
            binding.edtAss8.text.isNotEmpty() ||
            binding.edtAss9.text.isNotEmpty() ||
            binding.edtAss10.text.isNotEmpty() ||
            binding.edtAss10.text.isNotEmpty() ||
            binding.edtExam1.text.isNotEmpty() ||
            binding.edtExam2.text.isNotEmpty() ||
            binding.edtExam3.text.isNotEmpty() ||
            binding.edtExam4.text.isNotEmpty() ||
            binding.edtExam5.text.isNotEmpty() ||
            binding.edtExam6.text.isNotEmpty() ||
            binding.edtExam7.text.isNotEmpty() ||
            binding.edtExam8.text.isNotEmpty() ||
            binding.edtExam9.text.isNotEmpty() ||
            binding.edtExam10.text.isNotEmpty()){
                //call doCalculations fun
                doCalculations()
        } else {
            //Print error message with Toast or Dialog box
            Toast.makeText(this, "Please input some values", Toast.LENGTH_LONG).show()
        }
    }

    //to control the calculation flow
    private fun doCalculations(){
        //creating array to hold the assessments
        //couldn't use loop cos of the names of the variables (edtAss...)
        val assessments = FloatArray(10)
        if (binding.edtAss1.text.isEmpty()) {
            assessments[0] = 0.0f
        } else {
            assessments[0] = binding.edtAss1.text.toString().toFloat()
        }
        if (binding.edtAss2.text.isEmpty()) {
            assessments[1] = 0.0f
        } else {
            assessments[1] = binding.edtAss2.text.toString().toFloat()
        }
        if (binding.edtAss3.text.isEmpty()) {
            assessments[2] = 0.0f
        } else {
            assessments[2] = binding.edtAss3.text.toString().toFloat()
        }
        if (binding.edtAss4.text.isEmpty()) {
            assessments[3] = 0.0f
        } else {
            assessments[3] = binding.edtAss4.text.toString().toFloat()
        }
        if (binding.edtAss5.text.isEmpty()) {
            assessments[4] = 0.0f
        } else {
            assessments[4] = binding.edtAss5.text.toString().toFloat()
        }
        if (binding.edtAss6.text.isEmpty()) {
            assessments[5] = 0.0f
        } else {
            assessments[5] = binding.edtAss6.text.toString().toFloat()
        }
        if (binding.edtAss7.text.isEmpty()) {
            assessments[6] = 0.0f
        } else {
            assessments[6] = binding.edtAss7.text.toString().toFloat()
        }
        if (binding.edtAss8.text.isEmpty()) {
            assessments[7] = 0.0f
        } else {
            assessments[7] = binding.edtAss8.text.toString().toFloat()
        }
        if (binding.edtAss9.text.isEmpty()) {
            assessments[8] = 0.0f
        } else {
            assessments[8] = binding.edtAss9.text.toString().toFloat()
        }
        if (binding.edtAss10.text.isEmpty()) {
            assessments[9] = 0.0f
        } else {
            assessments[9] = binding.edtAss10.text.toString().toFloat()
        }

        //creating array to hold exam score
        val exam_scores = FloatArray(assessments.size)
        if (binding.edtExam1.text.isEmpty()) {
            exam_scores[0] = 0.0f
        } else {
            exam_scores[0] = binding.edtExam1.text.toString().toFloat()
        }
        if (binding.edtExam2.text.isEmpty()) {
            exam_scores[1] = 0.0f
        } else {
            exam_scores[1] = binding.edtExam2.text.toString().toFloat()
        }
        if (binding.edtExam3.text.isEmpty()) {
            exam_scores[2] = 0.0f
        } else {
            exam_scores[2] = binding.edtExam3.text.toString().toFloat()
        }
        if (binding.edtExam4.text.isEmpty()) {
            exam_scores[3] = 0.0f
        } else {
            exam_scores[3] = binding.edtExam4.text.toString().toFloat()
        }
        if (binding.edtExam5.text.isEmpty()) {
            exam_scores[4] = 0.0f
        } else {
            exam_scores[4] = binding.edtExam5.text.toString().toFloat()
        }
        if (binding.edtExam6.text.isEmpty()) {
            exam_scores[5] = 0.0f
        } else {
            exam_scores[5] = binding.edtExam6.text.toString().toFloat()
        }
        if (binding.edtExam7.text.isEmpty()) {
            exam_scores[6] = 0.0f
        } else {
            exam_scores[6] = binding.edtExam7.text.toString().toFloat()
        }
        if (binding.edtExam8.text.isEmpty()) {
            exam_scores[7] = 0.0f
        } else {
            exam_scores[7] = binding.edtExam8.text.toString().toFloat()
        }
        if (binding.edtExam9.text.isEmpty()) {
            exam_scores[8] = 0.0f
        } else {
            exam_scores[8] = binding.edtExam9.text.toString().toFloat()
        }
        if (binding.edtExam10.text.isEmpty()) {
            exam_scores[9] = 0.0f
        } else {
            exam_scores[9] = binding.edtExam10.text.toString().toFloat()
        }

        //creating array to hold the credit hours
        val credits = FloatArray(assessments.size)
        credits[0] = binding.spnCredit1.selectedItem.toString().toFloat()
        credits[1] = binding.spnCredit2.selectedItem.toString().toFloat()
        credits[2] = binding.spnCredit3.selectedItem.toString().toFloat()
        credits[3] = binding.spnCredit4.selectedItem.toString().toFloat()
        credits[4] = binding.spnCredit5.selectedItem.toString().toFloat()
        credits[5] = binding.spnCredit6.selectedItem.toString().toFloat()
        credits[6] = binding.spnCredit7.selectedItem.toString().toFloat()
        credits[7] = binding.spnCredit8.selectedItem.toString().toFloat()
        credits[8] = binding.spnCredit9.selectedItem.toString().toFloat()
        credits[9] = binding.spnCredit10.selectedItem.toString().toFloat()

        //total credit hours
        var total_credits = 0.0f
        for (i in credits.indices) {
            total_credits += credits[i]
        }

        //setting grade points for each total score
        val gradePoints = FloatArray(assessments.size)
        setGradePoint(assessments, exam_scores, gradePoints)

        //grade Point X credit
        val gradeByCredit = FloatArray(10)
        for (j in gradeByCredit.indices) {
            gradeByCredit[j] = gradePoints[j] * credits[j]
        }

        //total grade by credit
        var total_grade_by_credit = 0.0f
        for (k in credits.indices) {
            total_grade_by_credit += gradeByCredit[k]
        }

        //find gpa
        val gpa: Float
        gpa = if (total_credits <= 0.0) {
            0.0f
        } else {
            total_grade_by_credit / total_credits
        }

        //set grades for each course
        val grades = arrayOfNulls<String>(10)
        setGrades(assessments, exam_scores, grades)

        //set remarks for each course
        val remarks = arrayOfNulls<String>(grades.size)
        setRemarks(grades, remarks)

        //set class for the student
        val gpa_class = setClass(gpa)

        //creating intent and pass the extra info to the Result class
        val intent = Intent(this, Result::class.java)
        intent.putExtra("course_grades", grades)
        intent.putExtra("course_remarks", remarks)
        intent.putExtra("studentGpa", gpa)
        intent.putExtra("studentClass", gpa_class)
        startActivity(intent)
    }

    //setting the grade points
   private fun setGradePoint(assessments: FloatArray, exam_scores: FloatArray, gradePoints: FloatArray) {
        for (i in gradePoints.indices) {
            if (assessments[i] + exam_scores[i] >= 80.0) {
                gradePoints[i] = 4.0f
            } else if (assessments[i] + exam_scores[i] >= 75.0) {
                gradePoints[i] = 3.5f
            } else if (assessments[i] + exam_scores[i] >= 70.0) {
                gradePoints[i] = 3.0f
            } else if (assessments[i] + exam_scores[i] >= 65.0) {
                gradePoints[i] = 2.5f
            } else if (assessments[i] + exam_scores[i] >= 60.0) {
                gradePoints[i] = 2.0f
            } else if (assessments[i] + exam_scores[i] >= 55.0) {
                gradePoints[i] = 1.5f
            } else if (assessments[i] + exam_scores[i] >= 50.0) {
                gradePoints[i] = 1.0f
            } else {
                gradePoints[i] = 0.0f
            }
        }
    }

    //method for setting the grades for each course
    private fun setGrades(assessments: FloatArray, exam_scores: FloatArray, grades: Array<String?>) {
        for (i in assessments.indices) {
            if (assessments[i] + exam_scores[i] >= 80.0) {
                grades[i] = "A"
            } else if (assessments[i] + exam_scores[i] >= 75.0) {
                grades[i] = "B+"
            } else if (assessments[i] + exam_scores[i] >= 70.0) {
                grades[i] = "B"
            } else if (assessments[i] + exam_scores[i] >= 65.0) {
                grades[i] = "C+"
            } else if (assessments[i] + exam_scores[i] >= 60.0) {
                grades[i] = "C"
            } else if (assessments[i] + exam_scores[i] >= 55.0) {
                grades[i] = "D+"
            } else if (assessments[i] + exam_scores[i] >= 50.0) {
                grades[i] = "D"
            } else if (assessments[i] + exam_scores[i] >= 1.0) {
                grades[i] = "E"
            } else {
                grades[i] = "None"
            }
        }
    }

    //setting the remarks
    private fun setRemarks(grades: Array<String?>, remarks: Array<String?>) {
        for (i in grades.indices) {
            if (grades[i] == "A") {
                remarks[i] = "Excellent"
            } else if (grades[i] == "B+") {
                remarks[i] = "Very Good"
            } else if (grades[i] == "B") {
                remarks[i] = "Good"
            } else if (grades[i] == "C+") {
                remarks[i] = "Average"
            } else if (grades[i] == "C") {
                remarks[i] = "Fair"
            } else if (grades[i] == "D+") {
                remarks[i] = "Pass"
            } else if (grades[i] == "D") {
                remarks[i] = "Weak Pass"
            } else if (grades[i] == "E") {
                remarks[i] = "Fail"
            } else {
                remarks[i] = "None"
            }
        }
    }

    //setting class for the student
    private fun setClass(gpa: Float): String {
        return if (gpa >= 3.6) {
            "First Class"
        } else if (gpa >= 3.0) {
            "2nd Class (Upper Division)"
        } else if (gpa >= 2.5) {
            "2nd Class (Lower Division)"
        } else if (gpa >= 2.0) {
            "3rd Class"
        } else if(gpa >= 1.0) {
            "Pass"
        } else {
            "Attempted..."
        }
    }

    fun showHelp(view: View) {
        startActivity(Intent(this, GPAHelpActivity::class.java))
    }
}