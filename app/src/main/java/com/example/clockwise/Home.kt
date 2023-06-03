package com.example.clockwise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import androidx.annotation.NonNull
import com.google.android.material.navigation.NavigationBarView

class Home : AppCompatActivity() {

    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        nav = findViewById(R.id.nav)

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.hours -> Toast.makeText(this@Home, "Home", Toast.LENGTH_LONG).show()
                R.id.projects -> Toast.makeText(this@Home, "Projects", Toast.LENGTH_LONG).show()
                R.id.reports -> Toast.makeText(this@Home, "Reports", Toast.LENGTH_LONG).show()
                R.id.profile -> Toast.makeText(this@Home, "Profile", Toast.LENGTH_LONG).show()
                else -> {}
            }
            true
        }
    }
}
