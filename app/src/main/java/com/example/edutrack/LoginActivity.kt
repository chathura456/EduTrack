package com.example.edutrack

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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


        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser!=null){
            database= FirebaseDatabase.getInstance().getReference("Users")

            database.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                if(it.exists()){
                    val utype=it.child("Type").value
                    val name=it.child("Name").value.toString()

                    if (utype.toString().trim()=="{Lecturer=}"){
                        val intent= Intent(this,LecWeicome::class.java)
                        intent.putExtra("name",name)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Welcome student page", Toast.LENGTH_LONG)
                            .show()
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

        btnsubmit.setOnClickListener {
            if (email.text.toString().trim().isNotEmpty()&&password.text.toString().trim().isNotEmpty())
                firebaseAuth.signInWithEmailAndPassword(email.text.toString().trim(),password.text.toString().trim()).addOnCompleteListener{
                    if(it.isSuccessful){
                        database= FirebaseDatabase.getInstance().getReference("Users")

                        database.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                            if(it.exists()){
                                val utype=it.child("Type").value
                                val name=it.child("Name").value.toString()

                                if (utype.toString().trim()=="{Lecturer=}"){
                                    val intent= Intent(this,LecWeicome::class.java)
                                    intent.putExtra("name",name)
                                    startActivity(intent)
                                }
                                else{
                                    Toast.makeText(this, "Welcome student page", Toast.LENGTH_LONG)
                                        .show()
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
