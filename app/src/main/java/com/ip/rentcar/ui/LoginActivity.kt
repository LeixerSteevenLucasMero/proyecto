package com.ip.rentcar.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import kotlinx.android.synthetic.main.activity_create_account.et_password
import kotlinx.android.synthetic.main.activity_init_login.*

class LoginActivity : AppCompatActivity() {

    private var email = ""
    private var password = ""

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_login)

        setupViewModelAndObserve()

        btn_init_login.setOnClickListener(View.OnClickListener {
            email = et_email_login.text.toString().trim()
            password = et_password_login.text.toString().trim()
            if (!email.isEmpty() && !password.isEmpty()){
                viewModel.loginUser(email, password)
                timer()
            }else{
                Toast.makeText(this, "mal", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun timer(){
        val intent = Intent(this, MainActivity::class.java)
        object: CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                startActivity(intent)
                finish()
            }
        }.start()
    }

    fun setupViewModelAndObserve() {
        viewModel = ViewModelProvider(this, MyViewModelFactory(UsersUseCase()))
            .get(MyViewModel::class.java)
        val userObserver = Observer<String> {

            /*
            if (!it.equals("")){
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("name", it);
                startActivity(intent)
                finish()
            } else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

             */

        }
        viewModel.getLoginUsersLiveData().observe(this, userObserver)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@LoginActivity)
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