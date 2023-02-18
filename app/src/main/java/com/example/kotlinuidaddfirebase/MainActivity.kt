package com.example.kotlinuidaddfirebase

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinuidaddfirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    var savedtext :String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        //load()


        binding.apply {

            textviewregister.setOnClickListener {
                startActivity(Intent(this@MainActivity,MainActivity2:: class.java))
            }

            btnlogin.setOnClickListener {
                progresbarlogin.visibility= View.VISIBLE
                chekkedlogin()
                firebaseAuth.signInWithEmailAndPassword(
                    edittextloginemail.text.toString(),
                    edittextloginpassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        progresbarlogin.visibility=View.GONE

                        save(edittextloginemail.text.toString())

                        val intent=Intent(this@MainActivity, MainActivity3::class.java)
                        intent.putExtra("email",savedtext)
                        intent.putExtra("email",savedtext)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity, "Login fialed", Toast.LENGTH_SHORT).show()
                        progresbarlogin.visibility=View.GONE
                    }
                }
            }
        }
    }
    fun chekkedlogin (){
        binding.apply {
            if (edittextloginemail.text.toString().isEmpty()){
                edittextloginemail.setError("Error")
            } else if(edittextloginpassword.text.toString().isEmpty()){
                edittextloginpassword.setError("Error")
            }else if (edittextloginpassword.text.toString().length < 8 ){
                edittextloginpassword.setError("No less than 8 characters")
            }
        }
    }

    fun save(text:String){
        val editor = getSharedPreferences("mrj", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("dev",text)
        editor.commit()
    }
    fun load(){
        val sharedPreferences = getSharedPreferences("mrj", MODE_PRIVATE)
        savedtext = sharedPreferences.getString("dev",null)
        if (savedtext != null) {
            val intent = Intent(this@MainActivity,MainActivity3::class.java)
            intent.putExtra("email", savedtext)
            startActivity(intent)
        }
    }

}
