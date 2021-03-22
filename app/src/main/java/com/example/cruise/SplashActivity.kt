package com.example.cruise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

         val mLoginButton: Button = findViewById(R.id.LoginBtn)

         mLoginButton.setOnClickListener {
             Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
             intent = Intent(this, LoginActivity::class.java)
             startActivity(intent)
         }

     }
}

