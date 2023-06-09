package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class LecExam : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var modulebtn: LinearLayout
    private lateinit var schedulebtn: LinearLayout
    private lateinit var materialsbtn: LinearLayout
    private lateinit var assing: LinearLayout
    private lateinit var homebtn: ImageView
    private lateinit var logoutbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        var type = intent.getStringExtra("type")
        var userName = intent.getStringExtra("name")

        var backBtn = findViewById<ImageView>(R.id.imageView)
        backBtn.setOnClickListener {
            val intent = Intent(this, StudentDashboard::class.java)
            intent.putExtra("name", userName)
            intent.putExtra("type", type)
            startActivity(intent)
        }


    }

}






