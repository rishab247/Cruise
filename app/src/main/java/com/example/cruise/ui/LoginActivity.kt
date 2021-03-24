package com.example.cruise.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.example.cruise.ui.Tabs.MainPage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth

    lateinit  var uidEtext: EditText
    lateinit  var emailEtext: EditText
    lateinit  var passwordEtext: EditText
    lateinit var database: FirebaseDatabase

    var TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         database = FirebaseDatabase.getInstance()


        val mLoginButton: Button = findViewById(R.id.LoginBtn)
        uidEtext = findViewById(R.id.editTextTextPersonName)
        emailEtext = findViewById(R.id.editTextTextPersonName2);
        passwordEtext = findViewById(R.id.editTextTextPassword2);
            auth = FirebaseAuth.getInstance()
        




        mLoginButton.setOnClickListener {
            Log.e("TAG", "onCreate: " + uidEtext.text.equals(null) + "z")
            var flag = 0;
            if(uidEtext.text.isEmpty()) {
                flag=1
                 Toast.makeText(this, "Invalid UID", Toast.LENGTH_SHORT).show();
            }
            if( emailEtext.text.isEmpty()) {
                flag=1
                 Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
            if(passwordEtext.text.isEmpty()) {
                flag=1
                 Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }

            if(flag==0){

                auth.signInWithEmailAndPassword(
                        emailEtext.text.toString(),
                        passwordEtext.text.toString()
                )
                    .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                        if (!task.isSuccessful) {
                            try {
                                throw task.exception!!
                            } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                                Log.d(TAG, "onComplete: invalid_email")
                                //creating new user
                                auth.createUserWithEmailAndPassword(
                                        emailEtext.text.toString(),
                                        passwordEtext.text.toString()
                                )
                                        .addOnCompleteListener(this) { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "createUserWithEmail:success")
                                                val user = auth.currentUser
                                                updateUI(user, true)
                                            } else {
                                                Log.w(
                                                        TAG,
                                                        "createUserWithEmail:failure",
                                                        task.exception
                                                )
                                                Toast.makeText(
                                                        baseContext, "Authentication failed.",
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                                updateUI(null, true)
                                            }
                                        }
                            } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                                Log.d(TAG, "onComplete: wrong_password")
                            } catch (e: Exception) {
                                Log.d(TAG, "onComplete: " + e.message)
                            }
                        } else {
                            val user = auth.currentUser
                            updateUI(user, false)
                        }
                    }
                    )



            }
        }




    }






    private fun nextActivity(register: Boolean) {
        emailEtext.setText("")
        passwordEtext.setText("")
        uidEtext.setText("")

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
    public override fun onStart() {
        super.onStart()
         val currentUser = auth.currentUser
        updateUI(currentUser, false)
    }

    private fun updateUI(currentUser: FirebaseUser?, register: Boolean) {
        if(currentUser!==null &&  register){
            //database
            var userInfo = User_Info();
            userInfo.Email  = emailEtext.text.toString()
            userInfo.Uid  = emailEtext.text.toString().substring(0, 9)
            userInfo.save(this@LoginActivity)

            val myRef: DatabaseReference = database.getReference("Private/User_Info/" + currentUser.uid.toString())
            myRef.setValue(userInfo)
            nextActivity(true)
            //stop progress bar

        }

        else if(currentUser!==null) {

            val myRef: DatabaseReference = database.getReference("Private/User_Info/" + currentUser.uid.toString())
             myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userInfo = dataSnapshot.getValue(User_Info::class.java)

                    if (userInfo != null) {
                        if( userInfo.Name.toString().equals("")){
                            var userInfo = User_Info();
                            userInfo.Email  = emailEtext.text.toString()
                            userInfo.Uid  = emailEtext.text.toString().substring(0, 9)
                            userInfo.save(this@LoginActivity)
                            val myRef: DatabaseReference = database.getReference("Private/User_Info/" + currentUser.uid.toString())
                            myRef.setValue(userInfo)
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
}