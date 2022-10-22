package com.edon.stutility.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.edon.stutility.BrowserActivity
import com.edon.stutility.R
import com.edon.stutility.models.Site

class OtherSitesAdapter(private var dataSet: ArrayList<Site>):
    RecyclerView.Adapter<OtherSitesAdapter.ViewHolder>() {

    //class describing the items on each card
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val siteName: TextView = itemView.findViewById(R.id.txtSiteName)
        val siteImage: ImageView = itemView.findViewById(R.id.imgSiteImage)

        //to work with the whole View itself
        val view = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the custom layout
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.other_sites_adapter_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //actual binding of data with views
        holder.siteName.text = dataSet[position].name
        holder.siteImage.setImageResource(dataSet[position].image)

        //onClicklistener for the view
        holder.view.setOnClickListener {
            if(dataSet[position].address != "none"){
                val intent = Intent(holder.view.context, BrowserActivity::class.java)
                intent.putExtra("address", dataSet[position].address)
                //this startActivity fun takes context, the intent to start and options
                startActivity(holder.view.context, intent, null)
            } else {
                Toast.makeText(holder.view.context, "Fix address: ${dataSet[position].name}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //return number of items in the array
    override fun getItemCount(): Int {
        return dataSet.size
    }
}