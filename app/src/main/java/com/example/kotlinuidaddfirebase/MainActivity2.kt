package com.example.kotlinuidaddfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinuidaddfirebase.databinding.ActivityMain2Binding
import com.example.kotlinuidaddfirebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    lateinit var  binding: ActivityMain2Binding
    lateinit var databaseReference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users")
        firebaseAuth= FirebaseAuth.getInstance()

        binding.apply {
            btnregister.setOnClickListener {
                progresbarregister.visibility=View.VISIBLE
                chekked()


                firebaseAuth.createUserWithEmailAndPassword(
                    binding.edittextemail.text.toString(),
                    binding.edittextpassword.text.toString(),
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        var uid= firebaseAuth.currentUser?.uid
                        Toast.makeText(this@MainActivity2, "Register successful", Toast.LENGTH_SHORT).show()
                        progresbarregister.visibility= View.INVISIBLE
                        val user=User(
                            username = edittextusername.text.toString(),
                            lastname = edittextlastname.text.toString(),
                            phone = edittextphone.text.toString(),
                            email = edittextemail.text.toString(),
                            password = edittextpassword.text.toString()
                        )
                        databaseReference.child(uid!!).setValue(user)
                    }else{
                        progresbarregister.visibility=View.INVISIBLE
                        Toast.makeText(this@MainActivity2, "register Filead", Toast.LENGTH_SHORT).show()
                    }
                }
                val intent=Intent(this@MainActivity2,MainActivity3::class.java)
                intent.putExtra("username", edittextusername.text.toString())
                intent.putExtra("username", edittextlastname.text.toString())
                startActivity(intent)

            }
        }


    }
    fun chekked (){
        val chekk:Boolean=false
        if (binding.edittextusername.text.toString().isEmpty()){
            binding.edittextusername.setError("Error")
        }else if (binding.edittextlastname.text.toString().isEmpty()){
            binding.edittextlastname.setError("Error")
        }else if (binding.edittextphone.text.toString().isEmpty()){
            binding.edittextphone.setError("Error")
        }else if (binding.edittextemail.text.toString().isEmpty()){
            binding.edittextemail.setError("Error")
        }else if (binding.edittextpassword.text.toString().isEmpty()){
            binding.edittextpassword.setError("Error")
        }else if (binding.edittextpassword.text.toString().length <8 ){
            binding.edittextpassword.setError("No less than 8 characters")
        }else{
           Toast.makeText(this@MainActivity2,"Nice",Toast.LENGTH_SHORT).show()
        }
    }
}