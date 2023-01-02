package com.ip.rentcar.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ip.rentcar.R
import com.ip.rentcar.domain.UsersUseCase
import com.ip.rentcar.ui.model.User
import com.ip.rentcar.viewModel.MyViewModel
import com.ip.rentcar.viewModel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_edit_account.*
import kotlinx.android.synthetic.main.activity_edit_account.et_address_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_ci_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_country_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_email_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_first_name_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_last_name_ed
import kotlinx.android.synthetic.main.activity_edit_account.et_phone_ed

class EditActivity: AppCompatActivity() {
    private var uidUser = ""
    private var firstName = ""
    private var lastName = ""
    private var phone = ""
    private var email = ""
    private var password = ""
    private var ci = ""
    private var country = ""
    private var city = ""
    private var address = ""

    var nameUser = ""
    var message = ""

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        readUserData()

        nameUser = intent.getStringExtra("name").toString()

        message = getString(R.string.welcome_main).plus(" <font color=#FF0000>")
            .plus(nameUser)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_edit_account_rental.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_edit_account_rental.setText(Html.fromHtml(message));
        }

        btn_edit_account_ed.setOnClickListener(View.OnClickListener {
            firstName = et_first_name_ed.text.toString().trim()
            lastName = et_last_name_ed.text.toString().trim()
            phone = et_phone_ed.text.toString().trim()
            email = et_email_ed.text.toString().trim()
            password = et_password_ed.text.toString().trim()
            ci = et_ci_ed.text.toString().trim()
            country = et_country_ed.text.toString().trim()
            city = et_city_ed.text.toString().trim()
            address = et_address_ed.text.toString().trim()
            if (!firstName.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !email.isEmpty()
                && !password.isEmpty() && !ci.isEmpty() && !country.isEmpty() && !city.isEmpty()
                && !address.isEmpty()){
                updateUser(User(uidUser, firstName, lastName, phone, email, password, ci, country,
                    city, address))
            }else{
                Toast.makeText(this, "mal", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun readUserData(){
        auth = Firebase.auth
        var uid = auth.currentUser?.uid.toString()
        var dataUser = User()
        database = Firebase.database.reference
        database.child("users").child(uid).get().addOnSuccessListener {
            dataUser = it.getValue<User>()!!
            uidUser = dataUser.uid
            firstName = dataUser.firstName
            lastName = dataUser.lastName
            phone = dataUser.phone
            email = dataUser.email
            password = dataUser.password
            ci = dataUser.ci
            country = dataUser.country
            city = dataUser.city
            address = dataUser.address

            et_first_name_ed.setText(firstName)
            et_last_name_ed.setText(lastName)
            et_phone_ed.setText(phone)
            et_email_ed.setText(email)
            et_password_ed.setText(password)
            et_ci_ed.setText(ci)
            et_country_ed.setText(country)
            et_city_ed.setText(city)
            et_address_ed.setText(address)

        }.addOnFailureListener{
        }
    }

    fun updateUser(user: User){
        /*
        var postValues = user.toMap()
        database = Firebase.database.reference
        val childUpdates = hashMapOf<String, Any>(
            "users/${user.uid}/" to postValues
        )
        database.updateChildren(childUpdates)
         */

        database.child("users").child(uidUser).setValue(user)
            .addOnSuccessListener {
                val userAuth = Firebase.auth.currentUser

                userAuth!!.updatePassword(user.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MenuActivity::class.java)
                            intent.putExtra("name", nameUser);
                            startActivity(intent)
                            finish()
                        }
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "mal", Toast.LENGTH_LONG).show()
            }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("name", nameUser);
        startActivity(intent)
        finish()
    }
}