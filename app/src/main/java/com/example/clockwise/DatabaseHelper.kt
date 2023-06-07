package com.example.clockwise

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name" + " VARCHAR," +
                "Surname" + " VARCHAR," +
                "Email" + " VARCHAR," +
                "Password" + " VARCHAR" +
                ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addUser(name : String, surname : String, email: String, password: String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put("Name", name)
        values.put("Surname", surname)
        values.put("Email", email)

        // Encrypt the password first before putting the password into the database.
        // Salt and hash the password.
        val pwdProcess = SaltHashPassword() // Creating an object to use the Salt and Hash Password class.
        var passwordConfig = pwdProcess.hashPassword(password)
        values.put("Password", passwordConfig)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        // db.close()

    }

    // below method is to get
    // all data from our database

    // Signing the user in
    @SuppressLint("Range")
    fun signinUser(emailCheck: String, passwordCheck: String): Boolean {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        // Creating an object for the database process.

        val pwdProcess = SaltHashPassword(); // Creating an object to use the Salt and Hash Password class.

        var validUser = false

        // below code returns a cursor to
        // read data from the database
        val rowExists = "SELECT Email, Password FROM " + TABLE_NAME + " WHERE Email = ?"
        val selectionArgs = arrayOf(emailCheck)
        val cursor = db.rawQuery(rowExists, selectionArgs)

        val enteredPasswordHash = pwdProcess.hashPassword(passwordCheck)

        if(cursor.moveToFirst()) {
            var databaseHashPassword = cursor.getString(cursor.getColumnIndex("Password"));

            if(enteredPasswordHash == databaseHashPassword) {
                validUser = true

            }
        }


        return validUser
    }

    @SuppressLint("Range")
    fun signinUser(emailCheck: String): Boolean {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        // Creating an object for the database process.

        var validUser = false

        // below code returns a cursor to
        // read data from the database
        val rowExists = "SELECT Email FROM " + TABLE_NAME + " WHERE Email = ?"
        val selectionArgs = arrayOf(emailCheck)
        val cursor = db.rawQuery(rowExists, selectionArgs)

        if(cursor.moveToFirst()) {
            validUser = true
        }


        return validUser
    }

    @SuppressLint("Range")
    fun returnID(email: String): String {
        val db = this.readableDatabase
        var capturedID = ""

        // Creating an object for the database process.

        // below code returns a cursor to
        // read data from the database
        val rowExists = "SELECT Id FROM " + TABLE_NAME + " WHERE Email = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.rawQuery(rowExists, selectionArgs)

        if(cursor.moveToFirst()) {
            capturedID = cursor.getString(cursor.getColumnIndex("Id")).toString();
        }

        return capturedID

    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Clockwise"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "User"

    }
}