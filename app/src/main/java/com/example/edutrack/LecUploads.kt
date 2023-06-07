package com.example.edutrack

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.PaintFlagsDrawFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.net.URI

class LecUploads : AppCompatActivity() {

    private lateinit var evtName : TextView
    private lateinit var evtAssoc : TextView
    private lateinit var btnupload : FloatingActionButton
    private lateinit var uri: Uri
    private lateinit var mStorage : StorageReference
    private lateinit var btnback : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lecturer_uploads)

        val PDF : Int = 1
        evtName = findViewById(R.id.evNamelbl)
        evtAssoc = findViewById(R.id.evAssoclbl)
        btnupload=findViewById(R.id.btnupload)
        btnback=findViewById(R.id.btn_back)

        mStorage= FirebaseStorage.getInstance().getReference(intent.getStringExtra("moduleId").toString())

        setValuesToViews()

        btnback.setOnClickListener{
            val title=intent.getStringExtra("title").toString()
            val name=intent.getStringExtra("name").toString()
            val intent = Intent(this, LecDashboard::class.java)
            intent.putExtra("title",title)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        btnupload.setOnClickListener(View.OnClickListener {
            view: View? ->  val intent = Intent()
            intent.type="application/pdf"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Select PDF"),PDF)
        })

        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                if (resultCode== RESULT_OK){
                    uri=data?.data!!
                    upload()
                }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun upload(){
        var pd=ProgressDialog(this)
        pd.setTitle("Uploading")
        pd.show()
        var mReference=mStorage.child(uri.lastPathSegment.toString())
        try {
            mReference.putFile(uri).addOnSuccessListener {
                taskSnapshot:UploadTask.TaskSnapshot?-> var url= taskSnapshot!!.uploadSessionUri
                pd.dismiss()
                Toast.makeText(this,"succesfully uploaded",Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
        }

    }




    private fun setValuesToViews() {
        evtName.text = intent.getStringExtra("moduleId") + " " +intent.getStringExtra("Name")
        evtAssoc.text = intent.getStringExtra("year")+ " "+ intent.getStringExtra("semester")

    }
}