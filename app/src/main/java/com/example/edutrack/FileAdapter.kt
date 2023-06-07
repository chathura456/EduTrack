package com.example.edutrack

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.EventAdapter
import com.example.edutrack.Modules
import com.example.edutrack.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import kotlin.math.log

class FileAdapter(private val files: List<Materials>) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private lateinit var myListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }
    fun setItemClickListner(clickListner: FileAdapter.onItemClickListner){
        myListner = clickListner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.module_viwer, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files[position]
        holder.bind(fileItem)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lesson: TextView = itemView.findViewById(R.id.lblName)
        private val nameid: TextView = itemView.findViewById(R.id.lblAssociation)
        private val click: TextView = itemView.findViewById(R.id.lblclick)
        val eventImg : ImageView = itemView.findViewById(R.id.eventImg)

        fun bind(fileItem: Materials) {

            val photos: IntArray = intArrayOf(R.drawable.img01, R.drawable.img02,R.drawable.img03,R.drawable.img04,R.drawable.ic_maths)
            val ran = Random()
            val i = ran.nextInt(photos.size)
            lesson.text = fileItem.lesson
            nameid.text=fileItem.idname
            click.text=fileItem.click
            eventImg.setImageResource(photos[i])


        }
    }

    private fun downloadFile(filePath: String) {
        val storageRef = FirebaseStorage.getInstance().getReference()
        var islandRef = storageRef.child("images/island.jpg")

        val localFile = File.createTempFile("images", "jpg")

        islandRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }
    }
}
