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

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val title = findViewById<TextView>(R.id.titleText)
        var type = intent.getStringExtra("type")
        var userName = intent.getStringExtra("name")

        var backBtn = findViewById<ImageView>(R.id.imageView)
        var backBtn2 = findViewById<Button>(R.id.btn_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, StudentDashboard::class.java)
            intent.putExtra("name",userName)
            intent.putExtra("type",type)
            startActivity(intent)
        }

        backBtn2.setOnClickListener {
            val intent = Intent(this, StudentDashboard::class.java)
            intent.putExtra("name",userName)
            intent.putExtra("type",type)
            startActivity(intent)
        }

        // Time slots
        val timeSlots = arrayOf(
            "9:00 AM-\n11:00 AM\n", "11:00 AM-\n1:00 PM\n",
            "12:00 AM-\n2:00 PM\n", "1:00 PM-\n3:00 PM\n", "2:00 PM-\n4:00 PM\n",
            "3:00 PM-\n5:00 PM\n",
        )

        var subjects = listOf("MAD", "","Maths", "","IAS","", "Crypto","", "SQA", "ITPM", )
        if(type=="Lecturer"){
            title.text = "Lecturer's Schedule"
           subjects = listOf("MAD", "Maths", "", "", "", "", "HCI", "")
        }else{
            title.text = "Year 03 Sem 01 Lecture Schedule"
        }

        // Add table header
        val headerRow = TableRow(this)
        headerRow.addView(createTextView("Time", true))
        headerRow.setPadding(5,5,5,5)
        for (i in 0 until 6) {
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






