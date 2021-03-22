package com.example.cruise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mLoginButton: Button = findViewById(R.id.LoginBtn)

        mLoginButton.setOnClickListener {
            intent = Intent(this, MainPage:: class.java)
            startActivity(intent)
        }
    }
}