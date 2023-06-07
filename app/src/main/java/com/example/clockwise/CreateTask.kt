package com.example.clockwise

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipDescription
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.UUID
import org.json.JSONObject
import java.util.ArrayList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateTask : AppCompatActivity() {

    private lateinit var btnEndTime: Button
    private lateinit var btnStartTime: Button
    private lateinit var btnDate: Button
    private lateinit var btnSubmitTask: Button
    private lateinit var categoryCaptureTarget: String
    private lateinit var btnCategoryAdd: ImageButton

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

    val database = Firebase.database
    // Creating a reference
    val myTaskRef = database.getReference("Tasks")

    var fbCategoryList = ArrayList<String>()

    val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
    val id = sharedPreference.getString("ID", "").toString();

    val childRef = myTaskRef.child(id).child("UserCategory") // Connecting to the node to insert the category.

    // Checking.
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)

        // Retrieve the categories from the realtime database.

        retrieveCategoryArray(id)

        // https://www.youtube.com/watch?v=jXSNobmB7u4&t=237s&ab_channel=FineGap
        autoCompleteTextView = findViewById(R.id.auto_complete_text)
        adapterItems = ArrayAdapter(this, R.layout.list_item, fbCategoryList) // fbCategoryList
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
        btnCategoryAdd = findViewById(R.id.btn_add_category)

        btnStartTime.setOnClickListener {
            setStartTime()
        }

        btnEndTime.setOnClickListener {
            setEndTime()
        }

        btnDate.setOnClickListener {
            setDate()
        }

        btnCategoryAdd.setOnClickListener {
            addCategory() // Adding the category entered.
        }

        btnSubmitTask.setOnClickListener {
            // Taking action once the button has been clicked.
            // Get the chosen title.

            val text_title = findViewById<TextView>(R.id.taskTitle)
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

            // Inserting the information into the ParentNodeDatabase.

                if (categoryCaptureTarget != null) {
                    uploadtoFirebase(id, text_description_final, categoryCaptureTarget , text_title_final, date_startime_final, date_endtime_final, date_chosen_final)
                } else {
                    uploadtoFirebase(id, text_description_final, text_category_final , text_title_final, date_startime_final, date_endtime_final, date_chosen_final)
                }

            // Making message
            Toast.makeText(this, "New Task has been added!", Toast.LENGTH_SHORT).show()

            val inflater: LayoutInflater = layoutInflater
            val layout: View = inflater.inflate(R.layout.toast_custom_layout, null)

            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout

            val toastText: TextView = layout.findViewById(R.id.toast_text)
            // toastText.text = "New ID for the Task is: " + returnNewTaskKey() + "\n + Title: " + returnNewTitle()

            toast.setGravity(Gravity.FILL, 0, 0) // Set the toast gravity to fill the screen
            toast.show()

            // Taking the user to the homepage.
            val intent = Intent(this@CreateTask, TaskList::class.java)
            startActivity(intent)

        }

    }

    fun retrieveCategoryArray(id: String) {

        val database = Firebase.database
        // Creating a reference
        val myTaskRef = database.getReference("Tasks")

        val childRef = myTaskRef.child(id).child("UserCategory")

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                fbCategoryList.clear() // Clear the ArrayList before adding new values

                for (categorySnapshot in dataSnapshot.children) {
                    val category = categorySnapshot.getValue(String::class.java)
                    category?.let {
                        fbCategoryList.add(it)
                    }
                }

                // Use the userCategoryList as needed here
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

    }
    fun uploadtoFirebase(id: String, description: String, category: String, title: String, startTime: String, endTime: String, normalDate: String) {

        val database = Firebase.database
        // Creating a reference
        val myTaskRef = database.getReference("Tasks")

        // Generate a unique key
        val uniqueKey = myTaskRef.push().key ?: ""

        // Create a new child node with the unique key
        val childRef = myTaskRef.child(id).child(category).child(uniqueKey)
        val tasked = Task(description, category, title, startTime, endTime, normalDate)
        childRef.setValue(tasked)

        if (categoryCaptureTarget != null) {
            val catRef = myTaskRef.child(id).child("UserCategory")
            val catArray = ArrayList<String>()
            catArray.add(categoryCaptureTarget)
            catRef.setValue(catArray)
        }
    }

    fun getExistingArrays() {

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
    data class TaskNode(
        val category: String,
        val description: String,
        val normalDate: String,
        val startTime: String,
        val endTime: String,
        val title: String
    ) {
        // Default (no-argument) constructor
        constructor() : this("", "", "", "", "", "")
    }

    fun addCategory() {
        val categoryEditText = EditText(this)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Category")
            .setView(categoryEditText)
            .setPositiveButton("Add") { dialogInterface: DialogInterface, _: Int ->
                categoryCaptureTarget = categoryEditText.text.toString()
                // Save the category to data model or perform any necessary actions
                // Update the UI to indicate the category was added
                dialogInterface.dismiss()
            }

            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()

    }

}


