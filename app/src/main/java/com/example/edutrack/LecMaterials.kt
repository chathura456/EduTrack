package com.example.edutrack

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.ArrayList

class LecMaterials : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var modulebtn: LinearLayout
    private lateinit var schedulebtn: LinearLayout
    private lateinit var materialsbtn: LinearLayout
    private lateinit var assing: LinearLayout
    private lateinit var homebtn: ImageView
    private lateinit var logoutbtn: ImageView
    private lateinit var header: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbRef : DatabaseReference
    private lateinit var modulelist : ArrayList<Materials>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lecture_home)

        recyclerView= findViewById(R.id.eventView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var userName = intent.getStringExtra("name")
        var type = intent.getStringExtra("type")

        recyclerView.setHasFixedSize(true)
        var backBtn = findViewById<ImageView>(R.id.imageView)
        backBtn.setOnClickListener {
            val intent = Intent(this, StudentDashboard::class.java)
            intent.putExtra("name",userName)
            intent.putExtra("type",type)
            startActivity(intent)
        }

        header = findViewById(R.id.header)

        header.text=intent.getStringExtra("title")
        var moduleName = intent.getStringExtra("ModuleName")



        val eventLst = listOf(
            Materials("Lesson 1", moduleName, "materials/lesson1.pdf","click to download"),
            Materials("Lesson 2", moduleName , "materials/lesson2.pdf","click to download"),
            Materials("Lesson 3", moduleName , "materials/lesson3.pdf","click to download"),
            Materials("Lesson 4", moduleName , "materials/lesson4.pdf","click to download"),
            Materials("Lesson 5", moduleName , "materials/lesson5.pdf","click to download"),
            Materials("Lesson 6", moduleName, "materials/lesson6.pdf","click to download"),
            Materials("Lesson 7", moduleName , "materials/lesson7.pdf","click to download"),
            Materials("Lesson 8", moduleName , "materials/lesson8.pdf","click to download"),
            Materials("Lesson 9", moduleName , "materials/lesson9.pdf","click to download"),
            Materials("Lesson 10", moduleName , "materials/lesson10.pdf","click to download"),
        )

       val madapter = MaterialAdapter(eventLst)
        recyclerView.adapter = madapter

        madapter.setItemClickListner(object : MaterialAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                downloadFile(eventLst[position].path.toString())
            }

        })


    }

    public fun downloadFile(filePath: String) {
        Toast.makeText(this, filePath, Toast.LENGTH_LONG)
            .show()
        var pd= ProgressDialog(this)
        pd.setTitle("Downloading")
        pd.show()
        val storageRef = FirebaseStorage.getInstance().getReference()


        var islandRef = storageRef.child(filePath)

        val localFile = File.createTempFile("lessons", "pdf")

        islandRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            pd.dismiss()
        }.addOnFailureListener {
            // Handle any errors
        }
    }



}