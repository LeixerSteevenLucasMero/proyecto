package com.ip.rentcar.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ip.rentcar.R
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_reservation.*

class ReservationActivity : AppCompatActivity() {

    var nameUser = ""
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        nameUser = intent.getStringExtra("name").toString()

        message = getString(R.string.welcome_main).plus(" <font color=#FF0000>")
            .plus(nameUser)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_reservation.setText(Html.fromHtml(message,Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_reservation.setText(Html.fromHtml(message));
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("name", nameUser);
        startActivity(intent)
        finish()
    }
}