package com.example.clockwise

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.ImageView


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.ViewTreeObserver
import android.widget.ImageButton


// For the intro page.
class Intro : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro);

        resizeImages();

        // Going to the Login page.
        button = findViewById(R.id.btn_continue_email);
        button.setOnClickListener {
            val intent = Intent(this@Intro, Login::class.java)
            startActivity(intent)
        }

    }
    fun resizeImages() {
        // Function to resize the images.

        // Define the desired dimensions for resizing
        val targetWidth = 200
        val targetHeight = 150

        // Get references to the image views
        val logoIcon = findViewById<ImageView>(R.id.logoIcon)
        val quickInfo = findViewById<ImageView>(R.id.quickInfo)
        val btnApple = findViewById<ImageButton>(R.id.btn_apple)
        val btnGoogle = findViewById<ImageButton>(R.id.btn_google)
        val btnSSO = findViewById<ImageButton>(R.id.btn_sso)

        // Use ViewTreeObserver to wait for the layout to be measured before resizing the images
        val viewTreeObserver = logoIcon.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                logoIcon.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Resize the images
                logoIcon.setImageBitmap(resizeImage(R.drawable.app_logo, logoIcon.width, logoIcon.height))
                quickInfo.setImageBitmap(resizeImage(R.drawable.intro_info_asset, quickInfo.width, quickInfo.height))
                btnApple.setImageBitmap(resizeImage(R.drawable.btn_apple, btnApple.width, btnApple.height))
                btnGoogle.setImageBitmap(resizeImage(R.drawable.btn_google, btnGoogle.width, btnGoogle.height))
                btnSSO.setImageBitmap(resizeImage(R.drawable.btn_sso, btnSSO.width, btnSSO.height))
            }
        })

    }

    // Function to resize the image
    fun resizeImage(resourceId: Int, width: Int, height: Int): Bitmap {
        // Load the image from the resources
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resourceId, options)

        // Calculate the desired sample size based on the target dimensions
        options.inSampleSize = calculateInSampleSize(options, width, height)

        // Decode the image with the calculated sample size
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeResource(resources, resourceId, options)

        // Resize the bitmap to the target dimensions
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    // Function to calculate the sample size for resizing
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}

