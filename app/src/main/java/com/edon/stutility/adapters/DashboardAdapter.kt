package com.edon.stutility.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.MainActivity
import com.edon.stutility.R
import com.edon.stutility.models.DBoardMenuItem

class DashboardAdapter(private val context: Context, private val dataSet: ArrayList<DBoardMenuItem>):
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dashboardItem: CardView = itemView.findViewById(R.id.dashboardCard)
        val dboardItemImage: ImageView = itemView.findViewById(R.id.imgDboardItemImage)
        val dboardItemTitle: TextView = itemView.findViewById(R.id.tvBoardItemTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.custom_dashboard_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            dboardItemImage.setImageResource(dataSet[position].icon)
            dboardItemTitle.text = dataSet[position].title

            //onclick of the card, passing the id of the item to the activity
            dashboardItem.setOnClickListener{
                if(context is MainActivity){
                    context.setOnClicks(dataSet[position].id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}