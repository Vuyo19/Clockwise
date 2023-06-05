package com.example.clockwise

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class Display_TotalHours : AppCompatActivity(){

    //creating string
    private lateinit var newCategory: String

    //creating arraylist
    val categoriesList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.total_hours)

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