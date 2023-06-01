package com.example.clockwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View

// For the intro page.
class Intro : AppCompatActivity() {

    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro);

        // Going to the Login page.
        button = findViewById(R.id.btn_continue_email);
        button.setOnClickListener {
            val intent = Intent(this@Intro, Login::class.java)
            startActivity(intent)
        }

    }


}

