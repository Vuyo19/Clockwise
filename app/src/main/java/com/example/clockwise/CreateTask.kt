package com.example.clockwise

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import java.util.*

class CreateTask : AppCompatActivity() {

    private lateinit var btnEndTime: Button
    private lateinit var btnStartTime: Button
    private lateinit var btnDate: Button

    val category = arrayOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5")
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)

        // https://www.youtube.com/watch?v=jXSNobmB7u4&t=237s&ab_channel=FineGap
        autoCompleteTextView = findViewById(R.id.auto_complete_text)
        adapterItems = ArrayAdapter(this, R.layout.list_item, category)
        autoCompleteTextView.setAdapter(adapterItems) // Add this line to set the adapter for AutoCompleteTextView

        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this@CreateTask, "Category: $item", Toast.LENGTH_SHORT).show()
        }

        // resizeImages();

        // Perform the action when the link is clicked.
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)


        // https://www.youtube.com/watch?v=xacLtzjI-E8&ab_channel=TheCodingChain
        btnEndTime = findViewById(R.id.btn_end_time)
        btnStartTime = findViewById(R.id.btn_start_time)
        btnDate = findViewById(R.id.btn_date)

        btnStartTime.setOnClickListener {
            setStartTime()
        }

        btnEndTime.setOnClickListener {
            setEndTime()
        }

        btnDate.setOnClickListener {
            setDate()
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


