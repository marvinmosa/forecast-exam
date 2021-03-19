package com.homecredit.exam.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.homecredit.exam.R
import com.homecredit.exam.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun onShowBackButton(isDisplay: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isDisplay)
        supportActionBar?.setDisplayShowHomeEnabled(isDisplay)
    }
}