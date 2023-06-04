package com.example.clockwise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Context


// For the login page.
class Login : AppCompatActivity() {


    private lateinit var back_button: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var btn_signin_now: Button


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

        // If the user clicks on the button to login.
        btn_signin_now = findViewById(R.id.btn_signin);

        // When the button gets clicked.
        btn_signin_now.setOnClickListener{

            // Capture the correct information after the user has attempted to login.
            val text_email = findViewById<EditText>(R.id.edit_email)
            val text_password = findViewById<EditText>(R.id.edit_password);

            val text_email_text = text_email.text.toString();
            val text_password_text = text_password.text.toString();

            // Checking if the account exists.
            accountExists(text_email_text, text_password_text);

        }


    }

    // Checking if the account exists based on entering the email address and password.
    fun accountExists(emailCheck: String, passwordCheck: String) {

        val db = DBHelper(this, null)

        // Checking if the user exists.
        val userExists = db.signinUser(emailCheck, passwordCheck);

        if (userExists) {
            Toast.makeText(this, "User exists!", Toast.LENGTH_SHORT).show()

            // Taking the user to the homepage.
            val intent = Intent(this@Login, GetStarted::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show()
        }
    }
}

