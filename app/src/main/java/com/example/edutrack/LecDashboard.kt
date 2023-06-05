package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.EventAdapter
import com.google.firebase.auth.FirebaseAuth
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

        getEventListData()



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
            intent.putExtra("name",name)
            startActivity(intent)
        }

        schedulebtn.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        homebtn.setOnClickListener {
            val intent = Intent(this, LecWeicome::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        materialsbtn.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        assing.setOnClickListener {
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }


        logoutbtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }





    }


    private fun getEventListData(){
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
                            val intent = Intent(this@LecDashboard,LecUploads::class.java)

                            //add extras
                            intent.putExtra("moduleId",eventLst[position].ID)
                            intent.putExtra("Name",eventLst[position].Name)
                            intent.putExtra("semester",eventLst[position].Sem)
                            intent.putExtra("year",eventLst[position].Year)
                            startActivity(intent)
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