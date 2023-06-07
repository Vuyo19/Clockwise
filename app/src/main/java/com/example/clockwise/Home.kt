package com.example.clockwise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.NonNull
import com.google.android.material.navigation.NavigationBarView

class Home : AppCompatActivity() {

    private lateinit var nav: BottomNavigationView
    private lateinit var button_create_task: Button
    private lateinit var button_view_task: Button
    private lateinit var button_view_total_hours: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        /*
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
        } */

       // Directing the user to the create task page if button clicked.
        button_create_task = findViewById<Button>(R.id.btn_create_task);
        button_view_task = findViewById<Button>(R.id.btn_view_tasks)
        button_view_total_hours = findViewById<Button>(R.id.btn_view_total_hours)

        button_create_task.setOnClickListener {
            val intent = Intent(this@Home, CreateTask::class.java)
            startActivity(intent)
        }

        button_view_task.setOnClickListener {
            val intent = Intent(this@Home, TaskList::class.java)
            startActivity(intent)
        }

        button_view_total_hours.setOnClickListener {
            val intent = Intent(this@Home, Display_TotalHours::class.java)
            startActivity(intent)
        }

    }
}
