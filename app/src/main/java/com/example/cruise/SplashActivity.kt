package com.example.cruise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
         auth = FirebaseAuth.getInstance()

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
            intent = Intent(this, MainPage::class.java)

            intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }

    }

}

