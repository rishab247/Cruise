package com.example.cruise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.ims.RegistrationManager
import android.util.Log

class ProfileActivity : AppCompatActivity() {
    var  registerMode:Boolean = false
    val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        registerMode =intent.getBooleanExtra("Register",false)


    }


    override fun onBackPressed() {
        Log.e(TAG, "onBackPressed: "+ registerMode)
        if(!registerMode)
        { super.onBackPressed() }
    }
}