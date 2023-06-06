package com.example.clockwise

public class Task( f_description: String, f_category: String, f_title: String, f_startTime: String, f_endTime: String, f_normalDate: String) {

    var description: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    var category: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    var title: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    var startTime: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    var endTime: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    var normalDate: String = ""
        // Custom Getter
        get() {
            return field
        }
        // Custom Setter
        set(value) {
            field = value
        }

    // Creating a constructor
    init {
        description = f_description
        category = f_category
        title = f_title
        startTime = f_startTime
        endTime = f_endTime
        normalDate = f_normalDate
    }

}
