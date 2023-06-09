package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.crudapp.EventAdapter
import com.google.firebase.auth.FirebaseAuth

class LecWelcome : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var modulebtn: LinearLayout
    private lateinit var schedulebtn: LinearLayout
    private lateinit var materialsbtn: LinearLayout
    private lateinit var assing: LinearLayout
    private lateinit var homebtn: ImageView
    private lateinit var logoutbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lecture_welcome)




        username = findViewById(R.id.lectureUsername)
        modulebtn = findViewById(R.id.modulebutton)
        schedulebtn = findViewById(R.id.schedulebutton)
        materialsbtn = findViewById(R.id.materialsbutton)
        assing = findViewById(R.id.assingmentbutton)
        homebtn = findViewById(R.id.homebtn)
        logoutbtn = findViewById(R.id.logoutbtn)

        username.text = intent.getStringExtra("name")
        val name=intent.getStringExtra("name")

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
            //val intent = Intent(this, LecWeicome::class.java)
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










    }

}