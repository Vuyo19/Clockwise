package com.example.clockwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.util.*

//Referencing:
//https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/
class Graph_TotalHours : AppCompatActivity() {

    private lateinit var dateFilterStart : String

    private lateinit var dateFilterEnd : String

    private lateinit var startDate : Date

    private lateinit var endDate : Date

    private lateinit var viewFilteredHours: LinearLayout

    private var cat_totalHours : Int = 0

    private var minHour : Double = 0.0

    private var maxHour : Double = 0.0

    private var task_hoursSpent : Double = 0.0

    private lateinit var taskDate : Date

    private lateinit var startTime : String

    private lateinit var endTime : String

    private lateinit var timeSpent : String

    private lateinit var id : String

    val totalHoursFound = mutableListOf<Double>()

    private var hourIndex : Double = 0.0

    lateinit var totalHrsGraph: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph_total_hours)

        //linking variable to layout graph
        totalHrsGraph = findViewById(R.id.Graph_TotalHrs)

        //setting graph title
        totalHrsGraph.title = "Your Hours Worked:"

        //referencing back button
        val btnBackMenu = findViewById<Button>(R.id.Btn_back_totalHrsGraph)

        // Clicking the button back to the main menu.
        btnBackMenu.setOnClickListener {
            val intent = Intent(this@Graph_TotalHours, Home::class.java)
            startActivity(intent)
        }

        //getting current user's ID
        val sharedPreference = getSharedPreferences("MY_SESSION", MODE_PRIVATE);
        id = sharedPreference.getString("ID", "").toString();

        //creating listener for selecting the date range
        findViewById<Button>(R.id.Btn_selectRangeGraph).setOnClickListener {
            val dateRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Please Select").setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
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

                //setting index to zero
                hourIndex = 0.0;

                //erasing any previous graph values
                totalHrsGraph.removeAllSeries();

                //calling method to fetch data
                fetchMinAndMax();
            }

        }
    }

    private fun fetchMinAndMax(){

        val database = Firebase.database

        // Creating a reference
        val hourRef = database.getReference("HourGoals")

        val tasksRef = hourRef.child(id)

        val parentLinearLayout: LinearLayout = findViewById(R.id.parentLayoutTask) // Replace with the actual ID of your parent layout

        val childRef = hourRef.child(id)

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (taskSnapshot in dataSnapshot.children) {

                    val containerLayout = layoutInflater.inflate(R.layout.task_template, null) as CardView

                    //creating unique key
                    val uniqueKey = taskSnapshot.key // Get the unique key of each child node
                    val taskValues = taskSnapshot.value as HashMap<*, *> // Retrieve the values of the child node as a HashMap

                    //saving values from the database
                    minHour = taskValues["txtMin"] as Double
                    maxHour = taskValues["txtMax"] as Double

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //showing message
                //Toast.makeText(this, "Operation Cancelled", Toast.LENGTH_SHORT).show()
            }
        })

        //calling method to add hour goals to graph
        AddMinAndMax();
    }

    private fun AddMinAndMax(){
        //adding min hours to graph
        val minSeries : LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                //adding horizontal line
                DataPoint(0.0, minHour)
            )
        )

        //adding max hours to graph
        val maxSeries : LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                //adding horizontal line
                DataPoint(0.0, maxHour)
            )
        )

        //setting colour and thickness for the lines
        minSeries.color = R.color.black
        minSeries.thickness = 2
        maxSeries.color = R.color.teal_700
        maxSeries.thickness = 2

        //setting a title for the line
        minSeries.title = "Min Hour Goal"
        maxSeries.title = "Max Hour Goal"

        //adding series to graph
        totalHrsGraph.addSeries(minSeries)
        totalHrsGraph.addSeries(maxSeries)

        // calling method to fetch the remaining data
        fetchData();
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
                    taskDate = taskValues["normalDate"] as Date
                    startTime = taskValues["startTime"] as String
                    endTime = taskValues["endTime"] as String


                    if(displayFilter(taskDate) == true) {
                        // calling method to calculate total hours spent on a task
                        calculateTotalTaskHours();
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

        //total time spent doing task
        task_hoursSpent = timeSpent.toDouble();

        //adding value to arrayList
        totalHoursFound.add(task_hoursSpent)

        //calling method to configure graph
        ConfigureGraph();
    }


    private fun ConfigureGraph(){

        for (hours in totalHoursFound){
            //creating series from array to add data to graph
            val graphHrs : LineGraphSeries<DataPoint> = LineGraphSeries(
                arrayOf(
                    //adding point in line graph
                    DataPoint(hourIndex, hours)
                )
            )
            //adding to index
            hourIndex++;

            //setting line color
            graphHrs.color = R.color.purple_200

            //adding data points to graph
            totalHrsGraph.addSeries(graphHrs)
        }

        //setting graph to be scalable
        totalHrsGraph.viewport.isScalable = true

        //setting graph to be scrollable
        totalHrsGraph.viewport.isScrollable = true

        //graph animation
        totalHrsGraph.animate()

        //making Y axis scalable
        totalHrsGraph.viewport.setScalableY(true)

        //making Y axis scrollable
        totalHrsGraph.viewport.setScrollableY(true)
    }
}