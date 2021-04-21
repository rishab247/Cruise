package com.example.cruise.Data

public class Messages(val message: String, val type: String, val time: Long, val seen: Boolean) {

    var mMessage: String = message
    get() = field

    val mType: String = type
    val mSeen: Boolean = seen
    val mTime: Long = time




}