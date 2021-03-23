package com.example.cruise.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cruise.R
import com.google.android.material.textfield.TextInputLayout
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    var registerMode: Boolean = false
    val TAG = "ProfileActivity"

    val RESULT_LOAD_IMAGE = 1
    var imageuri: Uri? = null
    var imageresponce: String? = null
    lateinit var mProfileImage: CircleImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val uidInput: TextInputLayout = findViewById(R.id.textInputLayout)
        val nameInput: TextInputLayout = findViewById(R.id.textInputLayout2)
        val mSaveButton: Button = findViewById(R.id.button)
        val mStatusInput: EditText = findViewById(R.id.status_id)
        mProfileImage = findViewById(R.id.circleImageView2)


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


        mProfileImage.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(Intent.createChooser(i, "Select Picture"), RESULT_LOAD_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageuri = data!!.data
            CropImage.activity(imageuri)
                .setAspectRatio(1, 1)
                .start(this)

            mProfileImage.setImageURI(imageuri)

        }
    }


    override fun onBackPressed() {
        Log.e(TAG, "onBackPressed: $registerMode")
        if (!registerMode) {
            super.onBackPressed()
        }
    }
}