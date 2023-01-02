package com.ip.rentcar.ui.model

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ip.rentcar.R
import com.ip.rentcar.ui.EditActivity
import kotlinx.android.synthetic.main.activity_menu.*

class ReservationActivity : AppCompatActivity() {

    var nameUser = ""
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        nameUser = intent.getStringExtra("name").toString()

        message = getString(R.string.welcome_main).plus(" <font color=#FF0000>")
            .plus(nameUser)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_name.setText(Html.fromHtml(message,Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_name.setText(Html.fromHtml(message));
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_settings -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("name", nameUser);
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}