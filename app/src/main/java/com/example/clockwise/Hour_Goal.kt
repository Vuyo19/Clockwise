package com.example.clockwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.properties.Delegates

class Hour_Goal : AppCompatActivity(){

    //creating variables
    private lateinit var backBtn : Button

    private lateinit var saveBtn : Button

    private lateinit var  minHours : EditText;

    private lateinit var maxHours : EditText

    //temp variables to save user input [waiting for database]
    private var userMinHrs : Int = 0;

    private var userMaxHrs : Int = 0;

    private lateinit var txtMin : String

    private lateinit var txtMax : String

    //database reference
    //private lateinit var clockwiseDB : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hour_goal)

        //connecting button to layout button
        backBtn = findViewById(R.id.Btn_back)

        //setting listener to return user to previous page
        backBtn.setOnClickListener{
            val intent = Intent(this@Hour_Goal,Login::class.java)

        }

        //connecting local properties to layout properties
        saveBtn = findViewById(R.id.Btn_saveHrs);
        minHours = findViewById(R.id.PlainTxt_minHours);
        maxHours = findViewById(R.id.PlainTxt_maxHours);

        saveBtn.setOnClickListener{
            //converting EditText to String
           txtMin = minHours.toString();

            //saving value to int
            userMinHrs = Integer.parseInt(txtMin);

            //converting EditText to String
            txtMax = maxHours.toString();

            //saving value to int
            userMaxHrs = Integer.parseInt(txtMax);

            /* gotta wait for some advances in the database first...
            //SAVING TO DATABASE
            clockwiseDB = FirebaseDatabase.getInstance().getReference("HourGoals")
            clockwiseDB.child(loggedUser).setValue(maxHours, minHours).addOnSuccessListener{
                //clearing EditText boxes
                findViewById<EditText>(R.id.PlainTxt_maxHours).text.clear()
                findViewById<EditText>(R.id.PlainTxt_minHours).text.clear()

                //showing success message
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                //error handling
                //showing success message
                Toast.makeText(this, "Operation Failed. Try Again", Toast.LENGTH_SHORT)
            }*/
        }
    }


}