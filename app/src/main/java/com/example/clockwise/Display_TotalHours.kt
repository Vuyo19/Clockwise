package com.example.clockwise

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.util.Date
import java.util.*
import kotlin.collections.ArrayList

class Display_TotalHours : AppCompatActivity(){


    //creating string
    private lateinit var newCategory: String

    private lateinit var dateFilterStart : String

    private lateinit var dateFilterEnd : String

    private lateinit var startDate : Date

    private lateinit var endDate : Date

    private lateinit var btnBackMenu: Button

    private lateinit var viewFilteredHours: LinearLayout

    private var cat_totalHours : Int = 0

    private var task_hoursSpent : Int = 0

    private lateinit var taskDate : Date

    private lateinit var startTime : String

    private lateinit var endTime : String

    private lateinit var timeSpent : String

    private lateinit var id : String



    //creating arraylist
    val categoriesList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.total_hours)

        //referencing back button
        val btnBackMenu = findViewById<Button>(R.id.Btn_back_totalHrs)

        //referencing linear layout
        viewFilteredHours = findViewById(R.id.viewTotalHrs)

        // Clicking the button back to the main menu.
        btnBackMenu.setOnClickListener {
            val intent = Intent(this@Display_TotalHours, Home::class.java)
            startActivity(intent)
        }

        //getting current user's ID
        val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
        id = sharedPreference.getString("ID", "").toString();

        //creating listener for selecting the date range
        findViewById<Button>(R.id.Btn_selectRange).setOnClickListener{
            val dateRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Please Select").setSelection(
                    Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())
                ).build()

            dateRange.show(supportFragmentManager, "dateRange_picker")

            dateRange.addOnPositiveButtonClickListener {
                val dateFormatting = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                //saving start date
                dateFilterStart = "${dateFormatting.format(it.first)}"

                //converting string to date
                startDate = dateFormatting.parse(dateFilterStart)

                //saving end date
                dateFilterEnd = "${dateFormatting.format(it.second)}"

                //converting string to date
                endDate = dateFormatting.parse(dateFilterEnd)

            }

            //calling method to fetch data
            fetchData();
        }
    }

    private fun fetchData() {

        val taskList: MutableList<Task> = mutableListOf()

        val database = Firebase.database

        // Creating a reference
        val myTaskRef = database.getReference("Tasks")

        val tasksRef = myTaskRef.child(id)

        val parentLinearLayout: LinearLayout = findViewById(R.id.parentLayoutTask) // Replace with the actual ID of your parent layout

        val childRef = myTaskRef.child(id)

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (taskSnapshot in dataSnapshot.children) {

                    val containerLayout = layoutInflater.inflate(R.layout.task_template, null) as CardView

                    //creating unique key
                    val uniqueKey = taskSnapshot.key // Get the unique key of each child node
                    val taskValues = taskSnapshot.value as HashMap<*, *> // Retrieve the values of the child node as a HashMap

                    //saving values from the database
                    val category = taskValues["category"] as String
                    taskDate = taskValues["normalDate"] as Date
                    startTime = taskValues["startTime"] as String
                    endTime = taskValues["endTime"] as String

                    //calling method to filter date
                    displayFilter(taskDate);

                    if(displayFilter(taskDate) == true) {
                        // Find the views within the container layout
                        val categoryTextView: TextView =
                            containerLayout.findViewById(R.id.categoryTextView)
                        val totalHoursTextView: TextView =
                            containerLayout.findViewById(R.id.totalHoursTextView)

                        // Set the values for the views
                        categoryTextView.text = "Category: $category"
                        totalHoursTextView.text = "Total Hours: $cat_totalHours"


                        //Adding the container layout to the linear layout
                        viewFilteredHours.addView(containerLayout)
                    }

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //showing message
                //Toast.makeText(this, "Operation Cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayFilter(dateCheck : Date) : Boolean {
        var filterSuccess = false

        if(dateCheck >= startDate && dateCheck <= endDate){

            filterSuccess = true
        }
        else{
            filterSuccess = false
        }

        return filterSuccess
    }

    private fun calculateTotalTaskHours(){
        var checkDiff = Duration.between(LocalTime.parse(startTime), LocalTime.parse(endTime))
        timeSpent = checkDiff.toHours().toString();

        //total time spent inside task
        task_hoursSpent = timeSpent.toInt();
    }

}