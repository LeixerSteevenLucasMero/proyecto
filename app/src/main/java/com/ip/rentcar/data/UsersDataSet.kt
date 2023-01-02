package com.ip.rentcar.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ip.rentcar.ui.model.User

class UsersDataSet() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    fun checkCurrentUser() : String{
        val user = Firebase.auth.currentUser
        var result = ""
        if (user != null) {
            result = user.displayName.toString()
        }
        return result
    }

    fun createAccount(user: User): User{
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userAuth = Firebase.auth.currentUser
                    var profile = userProfileChangeRequest {
                        displayName = user.firstName + " " + user.lastName }
                    userAuth!!.updateProfile(profile).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user.uid = auth.uid.toString()
                            writeNewUser(user, auth.uid.toString())
                        }
                    }

                } else {

                }
            }

        return user
    }

    fun writeNewUser(user: User, uid: String) {
        database = Firebase.database.reference
        database.child("users").child(uid).setValue(user)
    }

    fun signIn(email: String, password: String): String {
        auth = Firebase.auth
        var result = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    result = user?.email.toString()
                    user?.let {
                        var name = ""
                        name = user.displayName.toString()
                        result = name.toString()
                    }
                } else {
                }
            }
        return result
    }

    fun createListUsers(): List<User> {
        return listOf(
            User("Manzana", "Rojo", "1234", "4444"),
            User("Banana","Amarillo", "1234", "4444"),
            User("Uvas","Verde", "1234", "4444"))
    }
}