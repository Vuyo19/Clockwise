package com.example.clockwise

import android.content.Intent
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

        // Creating an object for the database process.
        val db = DBHelper(this, null)

        btn_signup_now = findViewById(R.id.btn_signup);

        // Variables to store the respective variables.
        // Capture the user details and storing the information inside the SQLite
        btn_signup_now.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it

            var firstNameEditText = findViewById<EditText>(R.id.edit_firstname)
            var firstName = firstNameEditText.text.toString() // Get the actual value.

            var lastNameEditText = findViewById<EditText>(R.id.edit_surname)
            var lastName = lastNameEditText.text.toString(); // Get the actual value.

            var emailEditText = findViewById<EditText>(R.id.edit_email);
            var email = emailEditText.text.toString(); // Get the actual value

            var passwordEditText = findViewById<EditText>(R.id.edit_password);
            var password = passwordEditText.text.toString(); // Get the actual value
            var passwordConfig = hashPasswordGenerate(password);


            // creating variables for values
            // in name and age edit texts
            // calling method to add
            // name to our database
            db.addUser(firstName, lastName, email, passwordConfig);

            // Clearing the text after signing up
            firstNameEditText.setText("");
            lastNameEditText.setText("");
            emailEditText.setText("");
            passwordEditText.setText("");

            // Taking the user to the login page to officially sign in
            // User will be redirected to the login page.
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)

        }

    }

    fun hashPasswordGenerate(requestedPassword: String): String {

        // Creating an object from the SaltHashPassword Class.
        val pwdProcess = SaltHashPassword(); // Creating an object to use the Salt and Hash Password class.

        // Variables for creating the Salt and Hash Password.
        var password: String;
        val salt = pwdProcess.generateSalt(); // Generating the salt
        var hashedPassword = pwdProcess.hashPassword(requestedPassword, salt); // Generating the hashedPassword.

        return hashedPassword; // returning the hashed Password back.
    }
}