package com.example.clockwise

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Display_TotalHours : AppCompatActivity(){

    //creating string
    private lateinit var newCategory: String

    private lateinit var startDate : String

    private lateinit var endDate : String

    //creating arraylist
    val categoriesList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.total_hours)

        //creating listener for selecting the date range
        findViewById<TextView>(R.id.Txt_dateRange).setOnClickListener{
            val dateRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Please Select").setSelection(
                    Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())
                ).build()

            dateRange.show(supportFragmentManager, "dateRange_picker")

            dateRange.addOnPositiveButtonClickListener {
                val dateFormatting = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                //saving start date
                startDate = "${dateFormatting.format(it.first)}"

                //saving end date
                endDate = "${dateFormatting.format(it.second)}"
            }
        }

        //referencing listview
        val hrsList = findViewById<ListView>(R.id.ListV_totalHrs)

        //adding values to arraylist
        categoriesList.add("Category1")
        categoriesList.add("Category2")
        categoriesList.add("Category3")
        categoriesList.add("Category4")

        //creating adapter
        val newAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, categoriesList)

        //setting listview adapter
        hrsList.adapter = newAdapter;


    }
}