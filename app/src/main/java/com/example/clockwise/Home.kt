package com.example.clockwise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class Home : AppCompatActivity() {

    private lateinit var nav: BottomNavigationView
    private lateinit var button_create_task: Button
    private lateinit var button_view_task: Button
    private lateinit var button_view_total_hours: Button
    private lateinit var button_set_daily_goal: Button
    private lateinit var taskTitle : String
    private lateinit var taskDate : String
    private lateinit var id : String
    val taskDatesFound = mutableListOf<String>()
    val taskTitlesFound = mutableListOf<String>()
    private lateinit var today : String
    private lateinit var taskName : String
    var diffInDays : Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //getting current date
        val formatThis = SimpleDateFormat("dd-MM-yyyy");
        today = formatThis.format(LocalDate.now());

        //clearing lists
        taskDatesFound.clear();
        taskTitlesFound.clear();

        //calling method to fetch task dates
        fetchData();

        /*
        nav = findViewById(R.id.nav)

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.hours -> Toast.makeText(this@Home, "Home", Toast.LENGTH_LONG).show()
                R.id.projects -> Toast.makeText(this@Home, "Projects", Toast.LENGTH_LONG).show()
                R.id.reports -> Toast.makeText(this@Home, "Reports", Toast.LENGTH_LONG).show()
                R.id.profile -> Toast.makeText(this@Home, "Profile", Toast.LENGTH_LONG).show()
                else -> {}
            }
            true
        } */

       // Directing the user to the create task page if button clicked.
        button_create_task = findViewById<Button>(R.id.btn_create_task);
        button_view_task = findViewById<Button>(R.id.btn_view_tasks)
        button_view_total_hours = findViewById<Button>(R.id.btn_view_total_hours)
        button_set_daily_goal = findViewById<Button>(R.id.btn_set_goal)


        button_create_task.setOnClickListener {
            val intent = Intent(this@Home, CreateTask::class.java)
            startActivity(intent)
        }

        button_view_task.setOnClickListener {
            val intent = Intent(this@Home, TaskList::class.java)
            startActivity(intent)
        }

        button_view_total_hours.setOnClickListener {
            val intent = Intent(this@Home, Display_TotalHours::class.java)
            startActivity(intent)
        }

        button_set_daily_goal.setOnClickListener {
            val intent = Intent(this@Home, Hour_Goal::class.java)
            startActivity(intent)
        }

    }

    private fun fetchData() {

        //getting current user's ID
        val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
        id = sharedPreference.getString("ID", "").toString();

        val taskList: MutableList<Task> = mutableListOf()

        val database = Firebase.database

        // Creating a reference
        val myTaskRef = database.getReference("Tasks")

        val tasksRef = myTaskRef.child(id)

        val parentLinearLayout: LinearLayout =
            findViewById(R.id.parentLayoutTask) // Replace with the actual ID of your parent layout

        val childRef = myTaskRef.child(id)

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (taskSnapshot in dataSnapshot.children) {

                    val containerLayout =
                        layoutInflater.inflate(R.layout.task_template, null) as CardView

                    //creating unique key
                    val uniqueKey = taskSnapshot.key // Get the unique key of each child node
                    val taskValues =
                        taskSnapshot.value as HashMap<*, *> // Retrieve the values of the child node as a HashMap

                    //saving values from the database
                    taskDate = taskValues["normalDate"] as String
                    taskTitle = taskValues["title"] as String

                    //adding found details to lists
                    taskDatesFound.add(taskDate)
                    taskTitlesFound.add(taskTitle)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //showing message
                //Toast.makeText(this, "Operation Cancelled", Toast.LENGTH_SHORT).show()
            }
        })

        //calling method to check if a reminder is needed
        callReminder();
    }

    private fun callReminder(){
        //Referencing:
        //https://www.baeldung.com/kotlin/difference-between-two-dates

        //formatting dates
        val formatting : DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy")

        for (date in taskDatesFound){
            //checking for the difference between the task date and current date
            val fromThisDate = LocalDate.parse(today, formatting)
            val toThisDate = LocalDate.parse(date, formatting)

            val diff = Period.between(fromThisDate, toThisDate);
            diffInDays = diff.days

            val taskIndex = taskDatesFound.indexOf(date);

            taskName = taskTitlesFound.get(taskIndex)

            if(diffInDays < 5){
                //calling method to create reminder
                createAlertReminder()
            }
        }

    }

    private fun createAlertReminder(){
        //Referencing:
        //https://www.javatpoint.com/kotlin-android-alertdialog

        //creating builder
        val alertBuilder = AlertDialog.Builder(this)

        //setting the title
        alertBuilder.setTitle("REMINDER")
        //setting the alert icon
        alertBuilder.setIcon(android.R.drawable.ic_dialog_alert)

        //creating message to be shown
        alertBuilder.setMessage("Task \"" + taskName + "\" is due in " + diffInDays
        + " days.")

        //adding an "OK" button
        alertBuilder.setPositiveButton("OK"){dialogInterface, which ->
            //showing message after closing
            Toast.makeText(applicationContext, "Reminder Closed.", Toast.LENGTH_LONG).show();
        }

        //creating the alert
        val reminder:AlertDialog = alertBuilder.create();
        //alert will not be cancelable
        reminder.setCancelable(false)
        //showing alert
        reminder.show()
    }
}
