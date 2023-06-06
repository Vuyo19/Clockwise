package com.example.clockwise

import java.util.HashMap

// Node to store the all the category, task related to that user.

public object LocalRealTimeDatabase {

    val TaskNode = HashMap<String, Any>()

    // For storing the task with the matching unique key
    val TaskID = HashMap<String, Any>()

    // Add the task inside the category.
    val CategoryNode = HashMap<String, Any>()

    // Add the category inside the User
    val UserNode = HashMap<String, Any>()


}

