package com.example.cruise.ui.Tabs

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cruise.Data.FriendsData
import com.example.cruise.Data.RecyclerAdapter
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FriendsFragment : Fragment() {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var memberData: ArrayList<User_Info>?= ArrayList<User_Info>()


    lateinit var user_info: User_Info
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    var currentUser: FirebaseUser? = null
    lateinit var myRef: DatabaseReference
    lateinit var mListView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_friends, container, false)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = mAuth.currentUser
        memberData = ArrayList()
//        swipeRefreshLayout = v.findViewById(R.id.swipe);
//        swipeRefreshLayout.canScrollVertically()
//        swipeRefreshLayout.setOnRefreshListener {
//           swipeRefreshLayout.isRefreshing = false
//        }
        user_info = User_Info()
        user_info.get(activity!!)

        loaddata()


        val context: Context = activity as FragmentActivity

        val fm: FragmentManager = (context as FragmentActivity)
                .supportFragmentManager

        // implementing the list view for friend list
        mListView = v.findViewById(R.id.listView)
//        val exampleList = generateDummyList(10)
        mListView.adapter = RecyclerAdapter(memberData!!, fm)
        mListView.layoutManager = LinearLayoutManager(context)
        mListView.setHasFixedSize(true)


        return v
    }

    private fun loaddata() {

        myRef = database.getReference("Public/member_list/")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    var data = snap.getValue(User_Info::class.java)
                    if (data != null) {
                        Log.e(TAG, "onDataChange: " + data.Uid)
                        if (user_info.Uid != data.Uid) {

                            memberData?.add(data)
                            mListView.adapter?.notifyDataSetChanged()

                        }
                    }



                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun generateDummyList(size: Int): List<FriendsData> {
        val list = ArrayList<FriendsData>()

        list.add(FriendsData("Tushar Tambi", "18BCS1088", "1"));
        list.add(FriendsData("Rishab AggarWal", "18BCS1767", "1"));
        list.add(FriendsData("Amit Kumar", "18BCS1001", "1"));
        list.add(FriendsData("Rohit Kumar", "18BCS1002", "1"));
        list.add(FriendsData("Aadarsh Singh", "18BCS1003", "1"));
        list.add(FriendsData("Aman Sharma", "18BCS1004", "1"));
        list.add(FriendsData("Aman Bhai", "18BCS1005", "1"));
        list.add(FriendsData("Harsh Yadav", "18BCS1006", "1"));
        list.add(FriendsData("Himanshu Gupta", "18BCS1007", "1"));
        list.add(FriendsData("Digvijay Meena", "18BCS1008", "1"));
        list.add(FriendsData("Roshan Meena", "18BCS1009", "1"));
        list.add(FriendsData("Aryan Shekhawat", "18BCS1010", "1"));
        list.add(FriendsData("Honey Singh", "18BCS1011", "1"));
        list.add(FriendsData("Ayush Aggarwal", "18BCS1012", "1"));

        return list
    }
}