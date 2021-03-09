package com.example.androsupport

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var loginButton: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = findViewById(R.id.LoginBtn)
        loginButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, loginActivity::class.java)
            startActivity(intent)
        })
    }
}