package com.example.edutrack

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var firebaseAuth:FirebaseAuth
private lateinit var database: DatabaseReference

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val btnsubmit=findViewById<Button>(R.id.btn_login)
        val btnRegShortcut = findViewById<TextView>(R.id.regShortcut)


        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser!=null){
            database= FirebaseDatabase.getInstance().getReference("Users")

            database.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                if(it.exists()){
                    val utype=it.child("type").value.toString().trim()
                    val name=it.child("name").value.toString().trim()
                    Log.i(TAG, utype)
                    if (utype=="Lecturer"){
                        val intent= Intent(this,StudentDashboard::class.java)

                        intent.putExtra("name",name)
                        intent.putExtra("type","Lecturer")
                        startActivity(intent)
                    }
                    else if (utype=="Student") {
                        val intent= Intent(this,StudentDashboard::class.java)
                       intent.putExtra("name",name)
                        intent.putExtra("type","Student")
                       // var year = it.child("Type").child("Student").child("year").value.toString()
                      //  intent.putExtra("year",year)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, utype, Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG)
                        .show()
                }

            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG)
                    .show()
            }

        }

        btnRegShortcut.setOnClickListener {
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnsubmit.setOnClickListener {
            if (email.text.toString().trim().isNotEmpty()&&password.text.toString().trim().isNotEmpty())
                firebaseAuth.signInWithEmailAndPassword(email.text.toString().trim(),password.text.toString().trim()).addOnCompleteListener{ it ->
                    if(it.isSuccessful){
                        database= FirebaseDatabase.getInstance().getReference("Users")

                        database.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                            if(it.exists()){
                                val utype=it.child("type").value
                                val name=it.child("name").value.toString()

                                if (utype=="Lecturer"){
                                    val intent= Intent(this,StudentDashboard::class.java)
                                    intent.putExtra("name",name)
                                    intent.putExtra("type","Lecturer")
                                    startActivity(intent)
                                }
                                else{
                                    //var year = it.child("Type").child("Student").child("year").value.toString()
                                    val intent= Intent(this,StudentDashboard::class.java)
                                    intent.putExtra("name",name)
                                    intent.putExtra("type","Student")
                                    startActivity(intent)
                                }
                            }
                            else{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG)
                                    .show()
                            }

                        }.addOnFailureListener{
                            Toast.makeText(this, "Failed", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    else{
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            val toast:Toast = Toast.makeText(this, "Invalid Email address !", Toast.LENGTH_LONG)
                            val view = toast.view
                            view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                            toast.show()
                            email.text.clear()
                            password.text.clear()

                        } catch (e: FirebaseNetworkException) {
                            Toast.makeText(this, "Fail to login! Network connection", Toast.LENGTH_LONG)
                                .show()
                            email.text.clear()
                            password.text.clear()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            val toast:Toast = Toast.makeText(this, "Email or Password incorrect !", Toast.LENGTH_LONG)
                            val view = toast.view
                            view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                            toast.show()
                            email.text.clear()
                            password.text.clear()
                        }

                    }
                }
            else{

                email.text.clear()
                password.text.clear()

                val toast:Toast = Toast.makeText(this, "Please enter your email and password !", Toast.LENGTH_LONG)
                val view = toast.view
                view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                toast.show()



            }



        }




    }

    /*
    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!= null){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

*/

}
