package com.example.cruise.ui.Tabs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.example.cruise.Data.FriendListAdapter
import com.example.cruise.Data.FriendsData
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.example.cruise.ui.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    private lateinit var logoutbtn: ImageView
    private lateinit var auth: FirebaseAuth
//    lateinit var user_info: User_Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)
        auth = FirebaseAuth.getInstance()
//        user_info.get(this)
//        logoutbtn = findViewById(R.id.logoutId)
//
//
//        logoutbtn.setOnClickListener{
//            val currentUser = auth.currentUser
//            if(currentUser!=null) {
//                auth.signOut()
//                finish()
//            }
//        }


        val mGotoProfile: ImageView = findViewById(R.id.GotoProfile)
        mGotoProfile.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        val mNotification: LottieAnimationView = findViewById(R.id.lottieAnimationView2);
        mNotification.setOnClickListener {
            val bottomsheet: BottomSheetDialog = BottomSheetDialog(this, R.style.Theme_Design_BottomSheetDialog)

            val bottomSheetView: View = LayoutInflater.from(applicationContext).inflate(R.layout.friend_request, findViewById(R.id.bottomSheet))

            bottomsheet.setContentView(bottomSheetView)
            val listView: RecyclerView? = bottomsheet.findViewById(R.id.friendList)

            var listData: ArrayList<User_Info>? = ArrayList<User_Info>()

            Log.e("Data in List", listData.toString())
            listView?.adapter = FriendListAdapter(listData!!)
            listView?.layoutManager = LinearLayoutManager(this)
//            listView?.setHasFixedSize(true)

            bottomsheet.show()

        }


        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        tab_viewpager.offscreenPageLimit = 2
        val tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)
    }

    private fun setupViewPager(tabViewpager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(FriendsFragment(), "Friends")
        adapter.addFragment(ChatFragment(), "Chat")
        adapter.addFragment(StoryFragment(), "Story")

        tabViewpager?.adapter = adapter
    }

    class ViewPagerAdapter : FragmentPagerAdapter {

        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        public constructor(supportFragmentManager: FragmentManager) : super(supportFragmentManager)

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }

    override fun onBackPressed() {


    }
}