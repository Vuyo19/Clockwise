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


