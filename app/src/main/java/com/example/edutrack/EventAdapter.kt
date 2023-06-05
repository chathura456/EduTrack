package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edutrack.Modules
import com.example.edutrack.R
import java.util.*

class EventAdapter (private val eventList : ArrayList<Modules>) : RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    private lateinit var myListner: onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setItemClickListner(clickListner: onItemClickListner){
        myListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.module_viwer,parent,false)
        return ViewHolder(itemView, myListner)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.lblEventName.text = currentEvent.ID+ " " + currentEvent.Name
        holder.lblAssociation.text = currentEvent.Year + " "+ currentEvent.Sem
        // holder.lblDescription.text = currentEvent.descrption
        var eId = currentEvent.ID




    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(view : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(view) {
        val lblEventName : TextView = itemView.findViewById(R.id.lblName)
        val lblAssociation : TextView = itemView.findViewById(R.id.lblAssociation)
        //val lblDescription : TextView = itemView.findViewById(R.id.lblDescription)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }

}