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


  private lateinit var firebaseAuth:FirebaseAuth
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)



        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val btnsubmit=findViewById<Button>(R.id.btn_login)
        val signupbtn=findViewById<LinearLayout>(R.id.btn_clear)

        firebaseAuth = FirebaseAuth.getInstance()

        btnsubmit.setOnClickListener {
            if (email.text.toString().trim().isNotEmpty()&&password.text.toString().trim().isNotEmpty())
                firebaseAuth.signInWithEmailAndPassword(email.text.toString().trim(),password.text.toString().trim()).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            Toast.makeText(
                                this,
                                "Email address is not registered",
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: FirebaseNetworkException) {
                            Toast.makeText(this, "Fail to login! Network connection", Toast.LENGTH_LONG)
                                .show()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                }
            else{
                Toast.makeText(this,"email and password required", Toast.LENGTH_LONG).show()

                val toast:Toast = Toast.makeText(this, "Please enter your name !", Toast.LENGTH_SHORT)
                val view = toast.view
                view?.background?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
                toast.show()



            }



        }

        signupbtn.setOnClickListener{
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
