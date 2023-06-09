package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentDashboard : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        var nameText = findViewById<TextView>(R.id.textView)
        var headingText = findViewById<TextView>(R.id.textView2)
        var logoutBtn = findViewById<ImageView>(R.id.logoutBtn)
        var coursesBtn = findViewById<ImageView>(R.id.View1)
        var scheduleBtn = findViewById<ImageView>(R.id.View2)
        var examinationBtn = findViewById<ImageView>(R.id.View3)
        var assignmentBtn = findViewById<ImageView>(R.id.View4)

        var userName = intent.getStringExtra("name")
        var type = intent.getStringExtra("type")

        if (type=="Student"){
            headingText.text = "Student Dashboard"
        }else{
            headingText.text = "Lecturer Dashboard"
        }

       // var year = intent.getStringExtra("year")
        nameText.text = "Hi, $userName"

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        coursesBtn.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("type",type)
            intent.putExtra("name",userName)
            if(type=="Student"){
                intent.putExtra("title","My Modules")
            }else{
                intent.putExtra("title","All Modules")
            }
            startActivity(intent)
        }

        scheduleBtn.setOnClickListener {
            val intent = Intent(this, LecSchedule::class.java)
            intent.putExtra("type",type)
            intent.putExtra("name",userName)
            startActivity(intent)
        }

        examinationBtn.setOnClickListener {
            val intent=Intent(this,LecExam::class.java)
            startActivity(intent)

        }

        assignmentBtn.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("name",userName)
            intent.putExtra("type",type)
            if(type=="Student"){
                intent.putExtra("title","Upcoming Assignments")
            }else{
                intent.putExtra("title","Upload/View Assignments")
            }
            startActivity(intent)
        }
    }
}