package com.example.clockwise

class MinAndMaxClass {
    public class MinAndMax( f_maxHours: String, f_minHours: String) {

        var txtMax: String = ""
            // Custom Getter
            get() {
                return field
            }
            // Custom Setter
            set(value) {
                field = value
            }

        var txtMin: String = ""
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
            txtMax = f_maxHours
            txtMin = f_minHours
        }
    }
}