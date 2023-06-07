package com.example.edutrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class LecSchedule : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var modulebtn: LinearLayout
    private lateinit var schedulebtn: LinearLayout
    private lateinit var materialsbtn: LinearLayout
    private lateinit var assing: LinearLayout
    private lateinit var homebtn: ImageView
    private lateinit var logoutbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)




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



        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        // Time slots
        val timeSlots = arrayOf(
            "8:00 AM -\n9:00 AM\n", "9:00 AM -\n10:00 AM\n", "10:00 AM -\n11:00 AM\n",
            "11:00 AM -\n12:00 PM\n", "12:00 PM -\n1:00 PM\n", "1:00 PM -\n2:00 PM\n"
        )

        // Subjects
        val subjects = listOf("MAD", "Maths", "IAS", "Crypto", "Java", "SQA", "ITPM")

        // Add table header
        val headerRow = TableRow(this)
        headerRow.addView(createTextView("Time", true))

        for (i in 0 until 5) {
            val dayTextView = createTextView(getDayName(i), true)
            headerRow.addView(dayTextView)
            headerRow.setBackgroundResource(R.drawable.tablebackground)
        }
        tableLayout.addView(headerRow)

        // Add time slots and randomly assign subjects to each day
        for (timeSlot in timeSlots) {
            val row = TableRow(this)
            val timeTextView = createTextView(timeSlot, true)
            row.addView(timeTextView)
            row.setBackgroundResource(R.drawable.rowbackground)

            val subjectsForDay = subjects.shuffled().take(5)
            for (subject in subjectsForDay) {
                val subjectTextView = createTextView(subject, false)
                row.addView(subjectTextView)
            }

            tableLayout.addView(row)

        }
    }

    private fun createTextView(text: String, isHeader: Boolean): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.gravity = Gravity.CENTER
        if (isHeader) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD)
        }
        return textView
    }

    private fun getDayName(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            0 -> "Monday\n"
            1 -> "Tuesday\n"
            2 -> "Wednesday\n"
            3 -> "Thursday\n"
            4 -> "Friday\n"
            else -> ""
        }
    }


    }






