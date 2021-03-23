package com.example.cruise.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cruise.Data.User_Info
import com.example.cruise.ProfileActivity
import com.example.cruise.R
import com.example.cruise.ui.Tabs.MainPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    val TAG = "SplashActivity"
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

         auth = FirebaseAuth.getInstance()
         database = FirebaseDatabase.getInstance()

         val mLoginButton: Button = findViewById(R.id.LoginBtn)

         mLoginButton.setOnClickListener {
             Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
             intent = Intent(this, LoginActivity::class.java)
             startActivity(intent)
         }

     }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if(currentUser!=null){
            updateUI(currentUser)
        }

    }
    private fun updateUI(currentUser: FirebaseUser?) {


          if(currentUser!==null) {
            val myRef: DatabaseReference = database.getReference("Private/User_Info/" + currentUser.uid.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userInfo = dataSnapshot.getValue(User_Info::class.java)

                    if (userInfo != null) {
                        if( userInfo.Name.toString().equals("")){

                            nextActivity(true)
                            //stop progress bar

                        }
                        else{
                            nextActivity(false)

                            //stop progress bar
                        }
                    }
                    else{
                        nextActivity(true)
                        //stop progress bar

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                    //stop progress bar

                }
            })
        }

    }
    private fun nextActivity(register: Boolean) {



        if(!register){

            intent = Intent(this, MainPage::class.java)

//        intent.setFlags(
//            Intent.FLAG_ACTIVITY_CLEAR_TOP or
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
//                    Intent.FLAG_ACTIVITY_NEW_TASK
//        )
            startActivity(intent)
        }else{

            intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("Register",true)
//        intent.setFlags(
//            Intent.FLAG_ACTIVITY_CLEAR_TOP or
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
//                    Intent.FLAG_ACTIVITY_NEW_TASK
//        )
            startActivity(intent)
        }
    }

}

