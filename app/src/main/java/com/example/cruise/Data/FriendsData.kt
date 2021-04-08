package com.example.cruise.Data

data class FriendsData(val name: String, val message: String) {
    var userName: String = name
    var lastMessage: String = message
}

//helper text
// data class ExampleItem(val imageResource: Int, val text1: String, val text2: String)