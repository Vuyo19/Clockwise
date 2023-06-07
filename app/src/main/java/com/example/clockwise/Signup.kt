package com.example.clockwise

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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


            var passwordConfirmEditText = findViewById<EditText>(R.id.edit_password_confirmation);
            var passwordConfirm = passwordConfirmEditText.text.toString();


            // Checking if the password matches with the password Confirmation.
            if(password == passwordConfirm) {

                // creating variables for values
                // in name and age edit texts
                // calling method to add
                // name to our database
                db.addUser(firstName, lastName, email, password);

                // Clearing the text after signing up
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                emailEditText.setText("");
                passwordEditText.setText("");

                // getting the currently logged in user.
                val userExists = db.signinUser(email);

                if (userExists) {
                    // Creating the session for the user by storing the ID.
                    createSession(email)
                }

                // Taking the user to the login page to officially sign in
                // User will be redirected to the login page.
                // Perform the action when the link is clicked.
                val intent = Intent(this@Signup, GetStarted::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Password must match with password confirmation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createSession(emailCheck: String) {
        val sharedPreference: SharedPreferences = getSharedPreferences("MY_SESSION", Context.MODE_PRIVATE)

        val db = DBHelper(this, null)

        val id = db.returnID(emailCheck)

        val editor: SharedPreferences.Editor = sharedPreference.edit()

        editor.putString("ID", id)

    }
}