package com.edon.stutility.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.R
import com.edon.stutility.models.Schedule
import com.edon.stutility.models.DayOfLecture

class DayWithCoursesAdapter(private val context: Context, private val dataSet: ArrayList<DayOfLecture>):
    RecyclerView.Adapter<DayWithCoursesAdapter.ViewHolder>() {
    //viewholder describing the parent layout(day and recycler view)
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dayName: TextView = itemView.findViewById(R.id.tvDayName)
        val dayNumberOfCourses: TextView = itemView.findViewById(R.id.tvDayNumberOfCourses)

        val recLectures: RecyclerView = itemView.findViewById(R.id.recDayWithCourses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.custom_day_with_courses_container,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayName.text = dataSet[position].dayName
        //display number of courses per day
        holder.dayNumberOfCourses.text = dataSet[position].coursesForDay.size.toString()

        setChildRecyclerView(holder.recLectures, dataSet[position].coursesForDay)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    //use this function to pass the data from the dataset to the child adapter
    private fun setChildRecyclerView(recyclerView: RecyclerView, data: ArrayList<Schedule>){
        val adapter = CourseForDayAdapter(context, data)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}