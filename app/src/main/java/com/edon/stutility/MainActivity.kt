/**
 * Developer: Hayford Edoonu (Edon)
 * Check AppVersioning.xml
 */

package com.edon.stutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.stutility.adapters.DashboardAdapter
import com.edon.stutility.databinding.ActivityMainBinding
import com.edon.stutility.models.DBoardMenuItem

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = DashboardAdapter(this, getData())
        binding.recDashboard.layoutManager = LinearLayoutManager(this)
        binding.recDashboard.adapter = adapter
    }
    //setOnClickListener fun
    fun setOnClicks(itemId: Int){
        //create intents for all the cards, calling the corresponding Activities and
        // starting them
        when(itemId){
            1 -> {
                //open ucc portal in the BrowseActivity
                val intent = Intent(this, BrowserActivity::class.java)
                intent.putExtra("address","https://portal.ucc.edu.gh//reset/login.php")
                startActivity(intent)
            }
            2 -> {
                //open ucc e-learning site in the BrowseActivity
                val intent = Intent(this, BrowserActivity::class.java)
                intent.putExtra("address","https://elearning.ucc.edu.gh/login/index.php")
                startActivity(intent)
            }
            3 -> {
                startActivity(Intent(this, OtherELearningActivity::class.java))
            }
            4 -> {
                //the intent created and passed as argument inside the startActivity() fun
                startActivity(Intent(this, Timetable::class.java))
            }
            5 -> {
                startActivity(Intent(this, NotesActivity::class.java))
            }
            6 -> {
                startActivity(Intent(this, TodoActivity::class.java))
            }
            7 -> {
                startActivity(Intent(this, CalculateGPA::class.java))
            }
            8 -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
            }
        }
    }

    //prepare data to be loaded into adapter
    private fun getData(): ArrayList<DBoardMenuItem>{
        val list = ArrayList<DBoardMenuItem>()
        list.add(DBoardMenuItem(1, R.drawable.portal, "Student Portal"))
        list.add(DBoardMenuItem(2, R.drawable.ucc_elearning, "UCC E-Learning Platform"))
        list.add(DBoardMenuItem(3, R.drawable.other_e_l, "Other E-Learning Platforms"))
        list.add(DBoardMenuItem(4, R.drawable.timetable, "Timetable"))
        list.add(DBoardMenuItem(5, R.drawable.note, "Notes"))
        list.add(DBoardMenuItem(6, R.drawable.todo_icon, "Todo List"))
        list.add(DBoardMenuItem(7, R.drawable.calc1, "Calculate GPA"))
        list.add(DBoardMenuItem(8, R.drawable.about, "About"))

        return list
    }
}