package com.example.edutrack

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnLogShortcut = findViewById<TextView>(R.id.logShortcut)
        val name1 = findViewById<TextView>(R.id.name)
        val email2 = findViewById<EditText>(R.id.email1)
        val password = findViewById<EditText>(R.id.password)
        val btnSubmit=findViewById<Button>(R.id.btn_reg)
        var stdRadioButton = findViewById<RadioButton>(R.id.radioButtonStd)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        firebaseAuth = FirebaseAuth.getInstance()

        btnLogShortcut.setOnClickListener{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        btnSubmit.setOnClickListener {
            var type = "";

            type = if(stdRadioButton.isChecked){
                "Student"
            }else{
                "Lecturer"
            }

            val name = name1.text.toString().trim()
            val email = email2.text.toString().trim()
            val pass = password.text.toString().trim()

            if(name.isEmpty()){
                name1.error = "Name Cannot be Empty"
            }else if(email.isEmpty()){
                email2.error = "Email Cannot be Empty"
            }else if(pass.isEmpty()){
                password.error = "Password Cannot be Empty"
            }
            else{

                try {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnSuccessListener{
                            var user = User(name,email,type)
                            dbRef.child(firebaseAuth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                                val intent = Intent(this,LoginActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                                Log.w(ContentValues.TAG, "Realtime Database Error :", it)
                            }
                            Toast.makeText(this, "New User Created", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", err)
                        }

                }catch (e:java.lang.Exception){

                }
            }
        }
    }
}

data class User (
    var Name : String?=null,
    var Email : String?=null,
    var Type : String?=null
)