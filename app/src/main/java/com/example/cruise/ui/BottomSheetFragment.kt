package com.example.cruise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

lateinit var receiver_data: User_Info
lateinit var sender_data: User_Info


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var nameText: TextView
    lateinit var uidText: TextView
    lateinit var statusText: TextView
    lateinit var requestbtn: Button

    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    var currentUser: FirebaseUser? = null
    lateinit var myRef: DatabaseReference

    //    lateinit var view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.friends_details, container, false)



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = mAuth.currentUser

        nameText = view.findViewById(R.id.nameTitle)
        uidText = view.findViewById(R.id.tvSubtitle)
        statusText = view.findViewById(R.id.appCompatTextView)
        requestbtn = view.findViewById(R.id.sendrequest)

        //    val intent = view.intent.getS
//            arguments
        var bundle: Bundle? = arguments
        receiver_data = bundle?.getParcelable("receiver_data")!!
        sender_data = User_Info()
        sender_data.get(activity!!.applicationContext)
        nameText.text = receiver_data.Name
        uidText.text = receiver_data.Uid
        statusText.text = receiver_data.status





        requestbtn.setOnClickListener {

            sendrequest()

        }
    }

    private fun sendrequest() {

        myRef = database.getReference("Private/friend_list/" + (currentUser?.uid ?: "error") + "/")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChild(receiver_data.RequestToken)) {

                    if (sender_data.Uid.equals("") || receiver_data.Uid.equals("")) return
                    val msgtoken = generatetoken()
                    sender_data.msgToken = msgtoken
                    receiver_data.msgToken = msgtoken

                    myRef = database.getReference("Private/friend_list/" + (currentUser?.uid
                            ?: "error") + "/" + receiver_data.RequestToken)
                    myRef.setValue(receiver_data)
                    sender_data.Token = ""
                    sender_data.time = System.currentTimeMillis().toString()
                    myRef = database.getReference("Public/incoming_request/" + receiver_data.RequestToken + "/" + sender_data.RequestToken)
                    myRef.setValue(sender_data)
                } else {
                    requestbtn.text = "Already sent"
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun generatetoken(): String {
        myRef = database.getReference("Public/member_list/")

        return myRef.push().getKey().toString()
    }

}
