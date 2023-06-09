package com.example.edutrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MaterialAdapter (private val eventList : List<Materials>) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>(){

    private lateinit var myListner: onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setItemClickListner(clickListner: onItemClickListner){
        myListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.module_viwer,parent,false)
        return ViewHolder(itemView, myListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photos: IntArray = intArrayOf(R.drawable.pdf)
        val ran = Random()
        val i = ran.nextInt(photos.size)
        val currentEvent = eventList[position]
        holder.lblEventName.text = currentEvent.lesson
        holder.lblAssociation.text = currentEvent.idname
        holder.click.text=currentEvent.click
        holder.eventImg.setImageResource(photos[i])
        // holder.lblDescription.text = currentEvent.descrption
        var path = currentEvent.path




    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(view : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(view) {
        val lblEventName : TextView = itemView.findViewById(R.id.lblName)
        val lblAssociation : TextView = itemView.findViewById(R.id.lblAssociation)
        val eventImg : ImageView = itemView.findViewById(R.id.eventImg)
         val click: TextView = itemView.findViewById(R.id.lblclick)
        //val lblDescription : TextView = itemView.findViewById(R.id.lblDescription)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }

}