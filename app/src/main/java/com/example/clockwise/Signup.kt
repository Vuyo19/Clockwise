package com.example.clockwise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


// For the sign up page.
class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var btn_signup_now: Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        btn_signup_now = findViewById(R.id.btn_signup);

        // Variables to store the respective variables.
        // Capture the user details and storing the information inside the SQLite


        btn_signup_now.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            var firstName = findViewById<EditText>(R.id.edit_firstname).toString()
            var lastName = findViewById<EditText>(R.id.edit_surname).toString();
            var email = findViewById<EditText>(R.id.edit_email).toString();
            var password = findViewById<EditText>(R.id.edit_password).toString();

            // creating variables for values
            // in name and age edit texts

            // calling method to add
            // name to our database
            db.addUser(firstName, lastName, email, password);

            // Toast to message on the screen
            Toast.makeText(this, firstName + " added to database", Toast.LENGTH_LONG).show();

        }

    }
}