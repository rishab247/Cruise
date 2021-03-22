package com.example.cruise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)
    }


//        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
//        val tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)
//
//        setupViewPager(tab_viewpager)
//        tab_tablayout.setupWithViewPager(tab_viewpager)
//    }
//
//    private fun setupViewPager(tabViewpager: ViewPager?) {
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//
//        adapter.addFragment(ChatFragment(), "Chat")
//        adapter.addFragment(FriendsFragment(), "Friends")
//        adapter.addFragment(StoryFragment(), "Story")
//
//        tabViewpager?.adapter = adapter
//    }
//
//    class ViewPagerAdapter : FragmentPagerAdapter {
//
//        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
//        private final var fragmentTitleList1: ArrayList<String> = ArrayList()
//
//        public constructor(supportFragmentManager: FragmentManager) : super(supportFragmentManager)
//
//        override fun getItem(position: Int): Fragment {
//            return fragmentList1.get(position)
//        }
//
//        override fun getCount(): Int {
//            return fragmentList1.size
//        }
//
//        fun addFragment(fragment: Fragment, title: String) {
//            fragmentList1.add(fragment)
//            fragmentTitleList1.add(title)
//        }
//    }
}