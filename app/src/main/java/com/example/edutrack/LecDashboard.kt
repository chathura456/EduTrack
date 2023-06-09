package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.EventAdapter
import com.google.firebase.database.*
import java.util.ArrayList

class LecDashboard : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var modulebtn: LinearLayout
    private lateinit var schedulebtn: LinearLayout
    private lateinit var materialsbtn: LinearLayout
    private lateinit var assing: LinearLayout
    private lateinit var homebtn: ImageView
    private lateinit var logoutbtn: ImageView
    private lateinit var header: TextView

    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventLst : ArrayList<Modules>
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lecture_home)

        eventRecyclerView = findViewById(R.id.eventView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.setHasFixedSize(true)
        eventLst = arrayListOf<Modules>()

        var type = intent.getStringExtra("type")
        var userName = intent.getStringExtra("name")
        getEventListData(type,intent.getStringExtra("title"), userName)


        header = findViewById(R.id.header)

        header.text=intent.getStringExtra("title")
        var backBtn = findViewById<ImageView>(R.id.imageView)
        backBtn.setOnClickListener {
            val intent = Intent(this, StudentDashboard::class.java)
            intent.putExtra("name",userName)
            intent.putExtra("type",type)
            startActivity(intent)
        }

    }


    private fun getEventListData(type:String?,title:String?,userName:String?){
        eventRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Modules")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventLst.clear()
                if(snapshot.exists()){
                    for(event in snapshot.children){
                        val eventData = event.getValue(Modules::class.java)
                        eventLst.add(eventData!!)
                    }
                    val eAdapter = EventAdapter(eventLst)
                    eventRecyclerView.adapter = eAdapter

                    eAdapter.setItemClickListner(object :EventAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {

                            if(type=="Student"){
                                if(title!="Upcoming Assignments"){
                                    val intent = Intent(this@LecDashboard, LecMaterials::class.java)
                                    intent.putExtra("ModuleName",eventLst[position].Name)
                                    intent.putExtra("title","Lecture Materials")
                                    intent.putExtra("name",userName)
                                    intent.putExtra("type",type)
                                    startActivity(intent)
                                }else{
                                    val intent = Intent(this@LecDashboard, LecUploads::class.java)
                                    intent.putExtra("title","Submit Assignments")
                                    intent.putExtra("moduleId", eventLst[position].ID)
                                    intent.putExtra("Name", eventLst[position].Name)
                                    intent.putExtra("semester", eventLst[position].Sem)
                                    intent.putExtra("year", eventLst[position].Year)
                                    intent.putExtra("type",type)
                                    intent.putExtra("name",userName)
                                    startActivity(intent)
                                }

                            }else{

                                val intent = Intent(this@LecDashboard,LecUploads::class.java)
                                //add extras
                                intent.putExtra("moduleId", eventLst[position].ID)
                                intent.putExtra("Name", eventLst[position].Name)
                                intent.putExtra("semester", eventLst[position].Sem)
                                intent.putExtra("year", eventLst[position].Year)

                                if(title=="Upload/View Assignments"){
                                    intent.putExtra("title", "Add Marks/View Assignments")
                                }else{
                                    intent.putExtra("title", "Add Lecture Materials")
                                }
                                intent.putExtra("name",userName)
                                intent.putExtra("type",type)
                                startActivity(intent)
                            }

                        }

                    })

                    eventRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}