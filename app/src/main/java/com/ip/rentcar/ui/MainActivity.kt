package com.ip.rentcar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ip.rentcar.R
import com.ip.rentcar.domain.UsersUseCase
import com.ip.rentcar.ui.model.User
import com.ip.rentcar.viewModel.MyViewModel
import com.ip.rentcar.viewModel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_init_login.*
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupViewModelAndObserve()
        viewModel.checkCurrentUser()

        tv_sign_up.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        })

        tv_login.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        /*setupViewModelAndObserve()
        btn_init_login.setOnClickListener {
            viewModel.getListUsers()
        }
         */
    }

    fun setupViewModelAndObserve() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(UsersUseCase()))
            .get(MyViewModel::class.java)
        val userObserver = Observer<String> {

            if (!it.equals("")){
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("name", it);
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
        viewModel.getStatusUsersLiveData().observe(this, userObserver)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity)
            .setMessage("Quieres salir?")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, whichButton ->
                super.onBackPressed() //Sale de Activity.
                finish()
            }
            .setNegativeButton("Cancelar") { dialog, whichButton ->
            }
            .show()
    }

}