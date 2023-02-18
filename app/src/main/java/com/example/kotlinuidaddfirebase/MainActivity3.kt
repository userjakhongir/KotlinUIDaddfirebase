package com.example.kotlinuidaddfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.example.kotlinuidaddfirebase.databinding.ActivityMain3Binding
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val view=binding.navigationview1.getHeaderView(0)
        val textView: TextView = view.findViewById(R.id.textviewfromheader)

        val intent=intent
        //var username=intent.getStringExtra("username")
       /// var lastname = intent.getStringExtra("lastname")
        var email = intent.getStringExtra("email")
        title=email

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users")

        binding.apply {
            imageviewtoolbar.setOnClickListener {
                drawerlayout.openDrawer(Gravity.LEFT )

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username=snapshot.child("username").getValue().toString()
                    val lastname=snapshot.child("lastname").getValue().toString()
                    textView.text="$username $lastname"
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            }
        }

    }
}