package com.example.cruise.Data

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log

class User_Info( ){
        var Name :String = ""
        var Uid :String=""
        var Email :String=""
        var Dplocation :String=""
         var status:String=""
         var Token:String=""
      var RequestToken:String=""
private lateinit  var pref :SharedPreferences




//
fun save(b: Context) {
    Log.e("TAG", "setfields: class  "+Uid )

    pref = b.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
    val editor: Editor = pref.edit()
    editor.putString("Name", Name);
    editor.putString("Uid", Uid);
    editor.putString("Email", Email);
    editor.putString("Dplocation", Dplocation);
    editor.putString("Status", status);
    editor.putString("Token", Token);
    editor.putString("RequestToken", RequestToken);
    editor.apply();


}
    fun get(b: Context) {
        pref = b.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);

        Name = pref.getString("Name", "").toString();
       Uid= pref.getString("Uid", "").toString();
        Email =pref.getString("Email", "").toString();
       Dplocation= pref.getString("Dplocation", "").toString();
        status =pref.getString("Status", "").toString();
        Token =pref.getString("Token", "").toString();
        RequestToken =pref.getString("RequestToken", "").toString();



    }
}