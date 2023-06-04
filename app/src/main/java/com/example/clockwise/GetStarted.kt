package com.example.clockwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GetStarted : AppCompatActivity() {

    private lateinit var button_get_started: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        // Directing the user to the Homepage.
        button_get_started = findViewById(R.id.btn_get_started);

        // Clicking the button to get the user to the homepage
        button_get_started.setOnClickListener {
            val intent = Intent(this@GetStarted, Home::class.java)
            startActivity(intent)
        }


    }
}