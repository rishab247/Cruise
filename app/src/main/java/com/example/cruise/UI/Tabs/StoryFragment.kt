package com.example.cruise.UI.Tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cruise.ARcore
import com.example.cruise.R


class StoryFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_story, container, false)

        val mButton: Button = v.findViewById(R.id.button2)

        mButton.setOnClickListener{
            val intent = Intent(context, ARcore::class.java)
            startActivity(intent)
        }
        return v
    }
}

