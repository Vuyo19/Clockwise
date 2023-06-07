package com.example.clockwise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


// Layout for viewing the current tasks.
class TaskList : AppCompatActivity() {

    // Finally add the User information inside the Task.
    val ParentNodeDatabase = LocalRealTimeDatabase.TaskNode

    //storing start date
    private lateinit var startDate : String

    //storing end date
    private lateinit var endDate : String

    val TaskNode = HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_lists)

        //creating listener for selecting the date range
        findViewById<TextView>(R.id.Btn_selectDate).setOnClickListener{
            val dateRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Please Select").setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
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

        populateTasks()
    }
    fun populateTasks() {

        val taskList: MutableList<Task> = mutableListOf()

        val database = Firebase.database

        // Creating a reference
        val myTaskRef = database.getReference("Tasks")

        val tasksRef = myTaskRef.child("4").child("Category 1")

        val parentLinearLayout: LinearLayout = findViewById(R.id.parentLayoutTask) // Replace with the actual ID of your parent layout

        val childRef = myTaskRef.child("4").child("Category 1")

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (taskSnapshot in dataSnapshot.children) {

                    val containerLayout = layoutInflater.inflate(R.layout.task_template, null) as CardView

                    val uniqueKey = taskSnapshot.key // Get the unique key of each child node
                    val taskValues = taskSnapshot.value as HashMap<*, *> // Retrieve the values of the child node as a HashMap

                    val title = taskValues["title"] as String
                    val description = taskValues["description"] as String
                    val category = taskValues["category"] as String

                    // Find the views within the container layout
                    val titleTextView: TextView = containerLayout.findViewById(R.id.titleTextView)
                    val categoryTextView: TextView = containerLayout.findViewById(R.id.categoryTextView)
                    val descriptionTextView: TextView = containerLayout.findViewById(R.id.descriptionTextView)

                    // Set the values for the views
                    titleTextView.text = "Title: $title"
                    categoryTextView.text = "Category: $category"
                    descriptionTextView.text = "Description: $description"

                    // Add the container layout to the parent LinearLayout
                    parentLinearLayout.addView(containerLayout)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval process
                // ...
            }
        })

    }

}

private fun Any?.get(s: String) {

}
