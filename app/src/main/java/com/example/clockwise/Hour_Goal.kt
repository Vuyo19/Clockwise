package com.example.clockwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class Hour_Goal : AppCompatActivity(){

    //creating variables
    private lateinit var backBtn : Button

    private lateinit var saveBtn : Button

    private lateinit var  minHours : EditText;

    private lateinit var maxHours : EditText

    private lateinit var txtMin : String

    private lateinit var txtMax : String

    //database reference
    private lateinit var clockwiseDB : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hour_goal)

        //getting current user's ID
        val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
        val id = sharedPreference.getString("ID", "").toString();

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

            //converting EditText to String
            txtMax = maxHours.toString();

            //SAVING TO DATABASE
            uploadtoFirebase(id, txtMax, txtMin)}
        }
    fun uploadtoFirebase(id: String, txtMax: String, txtMin: String)
    {
        //database reference
        val clockwiseDB = Firebase.database

        // Creating a reference
        val hourGoals = clockwiseDB.getReference("HourGoals")

        // Create a new child node with the unique key
        val childRef = hourGoals.child(id)
        val saveGoals = MinAndMaxClass.MinAndMax(txtMax,txtMin)
        childRef.setValue(saveGoals)

        //clearing EditText boxes
        findViewById<EditText>(R.id.PlainTxt_maxHours).text.clear()
        findViewById<EditText>(R.id.PlainTxt_minHours).text.clear()

        //showing success message
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
    }
}



