package com.example.cruise.UI.Tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cruise.R


class StoryFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //setTheme(R.style.AppTheme)
        val v = inflater.inflate(R.layout.fragment_story, container, false)


        return v
    }
}

