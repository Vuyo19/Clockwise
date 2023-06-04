package com.example.clockwise

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.ImageView

class CreateTask  : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro);

        // resizeImages();

        // Perform the action when the link is clicked.
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)



    }
}