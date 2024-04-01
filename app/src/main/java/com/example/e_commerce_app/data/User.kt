package com.example.e_commerce_app.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imagePath: String = ""
){
    constructor(): this("","","","")
}
