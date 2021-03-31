    package com.example.cruise.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cruise.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class BottomSheetFragment: BottomSheetDialogFragment() {
    lateinit var sendRequest:Button
//    lateinit var view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


         var view  = inflater.inflate(R.layout.friends_details, container, false)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendRequest  = view.findViewById(R.id.sendrequest);


        sendRequest.setOnClickListener{
             Log.e(TAG, "onViewCreated: ", )
                
        }
    }
}