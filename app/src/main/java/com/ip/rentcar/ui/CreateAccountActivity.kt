package com.ip.rentcar.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ip.rentcar.R
import com.ip.rentcar.domain.UsersUseCase
import com.ip.rentcar.ui.model.User
import com.ip.rentcar.viewModel.MyViewModel
import com.ip.rentcar.viewModel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private var firstName = ""
    private var lastName = ""
    private var phone = ""
    private var email = ""
    private var password = ""
    private var ci = ""
    private var country = ""
    private var city = ""
    private var address = ""

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        setupViewModelAndObserve()
        viewModel.checkCurrentUser()

        btn_create_account.setOnClickListener(View.OnClickListener {
            firstName = et_first_name.text.toString().trim()
            lastName = et_last_name.text.toString().trim()
            phone = et_phone.text.toString().trim()
            email = et_email.text.toString().trim()
            password = et_password.text.toString().trim()
            ci = et_ci.text.toString().trim()
            country = et_country.text.toString().trim()
            city = et_city.text.toString().trim()
            address = et_address.text.toString().trim()
            if (!firstName.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !email.isEmpty()
                && !password.isEmpty() && !ci.isEmpty() && !country.isEmpty() && !city.isEmpty()
                && !address.isEmpty()){
                viewModel.createUser(User("", firstName, lastName, phone, email, password, ci, country,
                    city, address))
            }else{
                Toast.makeText(this, "mal", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun setupViewModelAndObserve() {
        viewModel = ViewModelProvider(this, MyViewModelFactory(UsersUseCase()))
            .get(MyViewModel::class.java)
        val userObserver = Observer<User> {

            if (!it.firstName.equals("")){
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("name", it.firstName + " " + it.lastName);
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            /*
            for (users in it) {
                Log.d("Users:", users.firstName)
            }

            for ((index, value) in it.withIndex()) {
                Log.d("Users $index:", value.lastName)
            }
             */
        }
        viewModel.getCreateUsersLiveData().observe(this, userObserver)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@CreateAccountActivity)
            .setMessage("Quieres salir?")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, whichButton ->
                super.onBackPressed() //Sale de Activity.
            }
            .setNegativeButton("Cancelar") { dialog, whichButton ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }
}