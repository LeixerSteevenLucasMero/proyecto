package com.ip.rentcar.ui.model

import com.google.firebase.database.Exclude

data class User(
    var uid:String = "",
    var firstName:String = "",
    var lastName:String = "",
    var phone:String = "",
    var email:String = "",
    var password:String = "",
    var ci:String = "",
    var country:String = "",
    var city:String = "",
    var address:String = "",
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "firstName" to firstName,
            "lastName" to lastName,
            "phone" to phone,
            "email" to email,
            "password" to password,
            "ci" to ci,
            "country" to country,
            "city" to city,
            "address" to address
        )
    }
}