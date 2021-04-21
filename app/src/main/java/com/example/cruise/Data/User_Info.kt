package com.example.cruise.Data

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class User_Info() : Parcelable {
    var Name: String = ""
    var Uid: String = ""
    var Email: String = ""
    var Dplocation: String = ""
    var status: String = ""
    var Token: String = ""
    var RequestToken: String = ""
    var msgToken: String = ""
    var time: String = ""
    private lateinit var pref: SharedPreferences

    constructor(parcel: Parcel) : this() {
        Name = parcel.readString().toString()
        Uid = parcel.readString().toString()
        Email = parcel.readString().toString()
        Dplocation = parcel.readString().toString()
        status = parcel.readString().toString()
        Token = parcel.readString().toString()
        RequestToken = parcel.readString().toString()
        msgToken = parcel.readString().toString()
    }


    //
    fun save(b: Context) {
        Log.e("TAG", "setfields: class  " + Uid)

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
    fun print(){
        Log.e("TAG", "onCreate: "+Name )
        Log.e("TAG", "onCreate: "+Uid )
        Log.e("TAG", "onCreate: "+ Email )
        Log.e("TAG", "onCreate: "+ Dplocation)
        Log.e("TAG", "onCreate: "+status )
        Log.e("TAG", "onCreate: "+ Token )
        Log.e("TAG", "onCreate: "+ msgToken )

    }



    fun get(b: Context) {
        pref = b.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);

        Name = pref.getString("Name", "").toString();
        Uid = pref.getString("Uid", "").toString();
        Email = pref.getString("Email", "").toString();
        Dplocation = pref.getString("Dplocation", "").toString();
        status = pref.getString("Status", "").toString();
        Token = pref.getString("Token", "").toString();
        RequestToken = pref.getString("RequestToken", "").toString();


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Uid)
        parcel.writeString(Email)
        parcel.writeString(Dplocation)
        parcel.writeString(status)
        parcel.writeString(Token)
        parcel.writeString(RequestToken)
        parcel.writeString(msgToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User_Info> {
        override fun createFromParcel(parcel: Parcel): User_Info {
            return User_Info(parcel)
        }

        override fun newArray(size: Int): Array<User_Info?> {
            return arrayOfNulls(size)
        }
    }


}