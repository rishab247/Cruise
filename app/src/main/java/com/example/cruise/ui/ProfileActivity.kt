package com.example.cruise.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cruise.R
import com.google.android.material.textfield.TextInputLayout

class ProfileActivity : AppCompatActivity() {
    var registerMode: Boolean = false
    val TAG = "ProfileActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val uidInput: TextInputLayout = findViewById(R.id.textInputLayout)
        val nameInput: TextInputLayout = findViewById(R.id.textInputLayout2)
        val mSaveButton: Button = findViewById(R.id.button)
        val mStatusInput: EditText = findViewById(R.id.status_id)


        uidInput.isEnabled = false
        nameInput.isEnabled = false
        mStatusInput.isEnabled = false


        registerMode = intent.getBooleanExtra("Register", false)

        val mEditProfile: ImageView = findViewById(R.id.editProfile)
        mEditProfile.setOnClickListener {
            uidInput.isEnabled = true
            nameInput.isEnabled = true
            mStatusInput.isEnabled = true
            mSaveButton.visibility = View.VISIBLE
        }

        mSaveButton.setOnClickListener {
            if (mSaveButton.isVisible) {
                mSaveButton.visibility = View.GONE
                uidInput.isEnabled = false
                nameInput.isEnabled = false
                mStatusInput.isEnabled = false
            }

        }
    }


    override fun onBackPressed() {
        Log.e(TAG, "onBackPressed: $registerMode")
        if (!registerMode) {
            super.onBackPressed()
        }
    }
}