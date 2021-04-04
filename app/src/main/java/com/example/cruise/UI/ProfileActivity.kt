package com.example.cruise.UI

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.example.cruise.UI.Tabs.MainPage
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    var registerMode: Boolean = false
    val TAG = "ProfileActivity"
    val RESULT_LOAD_IMAGE = 1
    var imageuri: Uri? = null
    var imageresponce: String? = null

    //View
    lateinit var mProfileImage: CircleImageView
    lateinit var   uidInput: TextInputLayout
    lateinit var   mUidInput: EditText
    lateinit var  nameInput: TextInputLayout
    lateinit var  mNameInput: EditText
    lateinit var  mSaveButton: Button
    lateinit var  mStatusInput: EditText
    lateinit var  mEmailInput: EditText
    lateinit var  mEditProfile: ImageView


    //DATABASE
    lateinit var user_info:User_Info
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
     var  currentUser : FirebaseUser? =null
    lateinit var myRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
         currentUser = mAuth.currentUser

        registerMode = intent.getBooleanExtra("Register", false)
        mUidInput = findViewById(R.id.first)
        uidInput = findViewById(R.id.textInputLayout)
        nameInput = findViewById(R.id.textInputLayout2)
        mNameInput = findViewById(R.id.second)
        mSaveButton = findViewById(R.id.button)
        mStatusInput = findViewById(R.id.status_id)
        mEditProfile = findViewById(R.id.editProfile)
        mEmailInput = findViewById(R.id.third)

        user_info = User_Info()
        user_info.get(this)
        setfields()
        val uidInput: TextInputLayout = findViewById(R.id.textInputLayout)
        val nameInput: TextInputLayout = findViewById(R.id.textInputLayout2)
        val mSaveButton: Button = findViewById(R.id.button)
        val mStatusInput: EditText = findViewById(R.id.status_id)
        mProfileImage = findViewById(R.id.circleImageView2)

        mProfileImage.setOnClickListener {
            Log.e(TAG, "onCreate: click")
            val i = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(Intent.createChooser(i, "Select Picture"), RESULT_LOAD_IMAGE)
        }


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
        if (registerMode) {
            mEditProfile.callOnClick()
            mEditProfile.isClickable = false
        }

        mSaveButton.setOnClickListener {
            if (mSaveButton.isVisible) {

                var inputflag = true
                if (mStatusInput.text.toString().trim().equals("")) {
                    inputflag = false
                    Toast.makeText(this, "Invalid Status", Toast.LENGTH_SHORT).show()
                }
                if (mNameInput.text.toString().trim().equals("")) {
                    inputflag = false
                    Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show()

                }

                if (inputflag) {
                    savedata()
                    mSaveButton.visibility = View.GONE
                    uidInput.isEnabled = false
                    nameInput.isEnabled = false
                    mStatusInput.isEnabled = false
                }
                if (registerMode && inputflag && currentUser != null) {
                    intent = Intent(this, MainPage::class.java)
                    finish()
                    startActivity(intent)


                }



            }


        }
    }

    private fun savedata() {
        user_info.Name = mNameInput.text.toString()
        user_info.status = mStatusInput.text.toString()

        myRef = database.getReference("Public/member_list/"  )
        if(registerMode) {
            user_info.RequestToken = myRef.push().getKey().toString()
            user_info.Token = myRef.push().getKey().toString()
        }
        Log.e(TAG, "savedata: "+user_info.RequestToken )
        user_info.save(this)


        myRef = database.getReference("Private/User_Info/" + (currentUser?.uid ?:"error" ))
        myRef.setValue(user_info)
        user_info.Token = ""
        myRef = database.getReference("Public/member_list/"  + user_info.RequestToken)
        myRef.setValue(user_info)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageuri = data!!.data
            CropImage.activity(imageuri)
                .setAspectRatio(1, 1)
                .start(this)
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            var result : CropImage.ActivityResult = CropImage.getActivityResult(data)

            if(resultCode == RESULT_OK){
                var resultUri : Uri = result.uri;

                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, resultUri)
                    val converetdImage: Bitmap = getResizedBitmap(bitmap, 200)
                    mProfileImage.setImageBitmap(converetdImage)

                }catch (e: Exception){
                    Log.e("Exception in image", e.message.toString())
                }
            }

        }
    }

    private fun getResizedBitmap(bitmap: Bitmap, i: Int): Bitmap {
        var width : Int = bitmap.width
        var height :Int  = bitmap.height

        var bitmapratio : Float = width.toFloat()/height
        if(bitmapratio > 1){
            width = i
            height = (width/bitmapratio).toInt()
        }else{
            height = i
            width = (height * bitmapratio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun setfields() {

        Log.e(TAG, "setfields: " + user_info.Email)
            if(!user_info.status.toString().trim().isEmpty())
             mStatusInput.setText(user_info.status);

            mNameInput.setText(user_info.Name);

        mUidInput.setText(user_info.Uid);
        mEmailInput.setText(user_info.Email)
//        mUidInput.setText(user_info.Uid);
    }


    override fun onBackPressed() {
        Log.e(TAG, "onBackPressed: $registerMode")
        if (!registerMode) {
            super.onBackPressed()
        }
        else{
            mAuth.signOut()
            finish()
        }
    }
}