package com.example.clockwise

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.TextView;
import androidx.core.text.HtmlCompat


// For the login page.
class Login : AppCompatActivity() {

    private lateinit var back_button: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Returning back to the login options page
        back_button = findViewById(R.id.btn_back_login);

        back_button.setOnClickListener {
            val intent = Intent(this@Login, Intro::class.java)
            startActivity(intent)
        }


        // If the user clicks on the link to sign up
        val signup_link = findViewById<TextView>(R.id.text_noaccount);

        signup_link.setOnClickListener {
            // Perform the action when the link is clicked.
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        // Capture the correct information after the user has attempted to login.

        val text_email = findViewById<EditText>(R.id.edit_email)
        val text_password = findViewById<EditText>(R.id.edit_password);



    }
}

