package com.example.clockwise

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class CreateTask : AppCompatActivity() {

    private lateinit var btnEndTime: Button
    private lateinit var btnStartTime: Button
    private lateinit var btnDate: Button
    private lateinit var btnSubmitTask: Button

    val category = arrayOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5")
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)

        val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
        val id = sharedPreference.getString("ID", "").toString();

        val database = Firebase.database

        // Creating a reference
        val myTaskRef = database.getReference("Tasks")


        // https://www.youtube.com/watch?v=jXSNobmB7u4&t=237s&ab_channel=FineGap
        autoCompleteTextView = findViewById(R.id.auto_complete_text)
        adapterItems = ArrayAdapter(this, R.layout.list_item, category)
        autoCompleteTextView.setAdapter(adapterItems) // Add this line to set the adapter for AutoCompleteTextView

        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this@CreateTask, "Category: $item", Toast.LENGTH_SHORT).show()
        }

        // https://www.youtube.com/watch?v=xacLtzjI-E8&ab_channel=TheCodingChain
        btnEndTime = findViewById(R.id.btn_end_time)
        btnStartTime = findViewById(R.id.btn_start_time)
        btnDate = findViewById(R.id.btn_date)
        btnSubmitTask = findViewById(R.id.Btn_saveHrs)

        btnStartTime.setOnClickListener {
            setStartTime()
        }

        btnEndTime.setOnClickListener {
            setEndTime()
        }

        btnDate.setOnClickListener {
            setDate()
        }

        btnSubmitTask.setOnClickListener {
            // Taking action once the button has been clicked.
            // Get the chosen title.

            val text_title = findViewById<TextView>(R.id.editTitle)
            val text_title_final = text_title.text.toString()

            // Get the chosen description
            val text_description = findViewById<EditText>(R.id.taskDescription)
            val text_description_final = text_description.text.toString()

            // Get the chosen date
            val date_chosen = findViewById<AppCompatButton>(R.id.btn_date)
            val date_chosen_final = date_chosen.text.toString();

            // Get the chosen startime.
            val date_startime = findViewById<AppCompatButton>(R.id.btn_start_time)
            val date_startime_final = date_startime.text.toString();

            // Get the chosen endTime. Alright
            val date_endtime = findViewById<AppCompatButton>(R.id.btn_end_time)
            val date_endtime_final = date_endtime.text.toString()

            // Get the category.
            val text_category = findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
            val text_category_final = text_category.text.toString()

            // Generate a unique key
            val uniqueKey = myTaskRef.push().key ?: ""

            // Create a new child node with the unique key
            val childRef = myTaskRef.child(id).child(text_category_final).child(uniqueKey)

            // Creating an object to store the newly added task.
            val tasked = Task(text_description_final, text_category_final, text_title_final, date_startime_final, date_endtime_final, date_chosen_final)
            childRef.setValue(tasked);

            /*
            myTask.push();
            myTask.child(id).child(text_category_final).setValue(tasked) // Adding the information into the Realtime Database.
            */

            // Making message
            Toast.makeText(this, "New Task has been added!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setStartTime() {
        // Creating local variables to store the selected values
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, selectedHour, selectedMinute ->
            val showTime = "$selectedHour : $selectedMinute"
            btnStartTime.text = showTime // Set the value of showDate to btnDate text

        }, hour, min, false)

        timePickerDialog.show()
    }

    private fun setEndTime() {
        // Creating local variables to store the selected values
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, selectedHour, selectedMinute ->
            val showTime = "$selectedHour : $selectedMinute"
            btnStartTime.text = showTime // Set the value of showDate to btnDate text
            // Implement  logic for handling the selected time
        }, hour, min, false)

        timePickerDialog.show()
    }


    private fun setDate() {
        // Creating local variables to store the selected values
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDate ->
            val showDate = "$selectedDate / ${selectedMonth + 1} / $selectedYear"
            btnDate.text = showDate // Set the value of showDate to btnDate text
        }, year, month, date)

        datePickerDialog.show()
    }

}


