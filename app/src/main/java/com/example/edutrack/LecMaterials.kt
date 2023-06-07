package com.example.edutrack

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.EventAdapter
import com.google.firebase.auth.FirebaseAuth
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lecture_home)



        recyclerView= findViewById(R.id.eventView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)




        username = findViewById(R.id.lectureUsername)
        modulebtn = findViewById(R.id.modulebutton)
        schedulebtn = findViewById(R.id.schedulebutton)
        materialsbtn = findViewById(R.id.materialsbutton)
        assing = findViewById(R.id.assingmentbutton)
        homebtn = findViewById(R.id.homebtn)
        logoutbtn = findViewById(R.id.logoutbtn)
        header = findViewById(R.id.header)

        username.text = intent.getStringExtra("name")
        val name=intent.getStringExtra("name")

        header.text=intent.getStringExtra("title")

        modulebtn.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("title","Upload Lecture Modules")
            intent.putExtra("name",name)
            startActivity(intent)
        }

        schedulebtn.setOnClickListener {
            val intent = Intent(this, LecSchedule::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        homebtn.setOnClickListener {
            val intent = Intent(this, LecWeicome::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        materialsbtn.setOnClickListener {
            val intent = Intent(this, LecMaterials::class.java)
            intent.putExtra("title","Download Materials")
            intent.putExtra("name",name)
            startActivity(intent)
        }

        assing.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("title","Upload Assingments")
            intent.putExtra("name",name)
            startActivity(intent)
        }


        logoutbtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val files = listOf(
            Materials("Lesson 1", "CS201.3 Software Architecture", "materials/lesson1.pdf","click to download"),
            Materials("Lesson 2", "SE201.3 Algorithm", "materials/lesson2.pdf","click to download"),
            Materials("Lesson 3", "CN201.3 Computer Network", "materials/lesson3.pdf","click to download"),
            Materials("Lesson 4", "MS201.3 Systems", "materials/lesson4.pdf","click to download"),
            Materials("Lesson 5", "SE301.3 Mobile Application", "materials/lesson5.pdf","click to download"),
            Materials("Lesson 1", "CS301.3  Mathematics", "materials/lesson6.pdf","click to download"),
            Materials("Lesson 1", "CS201.3 Business Process", "materials/lesson7.pdf","click to download"),
        )

       val adapter = FileAdapter(files)
        recyclerView.adapter = adapter

        adapter.setItemClickListner(object :FileAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {


                downloadFile("materials/lesson3.pdf")
            }

        })


    }

    public fun downloadFile(filePath: String) {
        var pd= ProgressDialog(this)
        pd.setTitle("Downloading")
        pd.show()
        val storageRef = FirebaseStorage.getInstance().getReference()
        var islandRef = storageRef.child("materials/lesson3.pdf")

        val localFile = File.createTempFile("lesson1", "pdf")

        islandRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }
    }



}