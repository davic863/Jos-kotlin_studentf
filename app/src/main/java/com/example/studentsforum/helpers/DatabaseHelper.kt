package com.example.studentsforum.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.studentsforum.model.Users

class DatabaseHelper(context:Context) :SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    //Create a table that will perform our SQL Query

    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_USER_NAME+ " TEXT,"
            + COLUMN_USER_EMAIL+ " TEXT,"+ COLUMN_USER_PASSWORD+ " TEXT," + COLUMN_USER_ADDRESS+ " TEXT " + COLUMN_USER_IMAGE+ " BLOB" +")"
            )





    //Create Query to drop our Table


    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase?) {

        if (db != null) {
            db.execSQL(CREATE_USER_TABLE)
        }

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        if (p0 != null) {
            p0.execSQL(DROP_USER_TABLE)
        }
        onCreate(p0)
    }

    // Create a function to add user Record
    /*fun addUser(users: Users){

        //db is an instance of a writable database that aid us to write to or update our database
        val db = this.writableDatabase

        val values  = ContentValues()
        values.put(COLUMN_USER_NAME,users.name)
        values.put(COLUMN_USER_EMAIL,users.email)
        values.put(COLUMN_USER_PASSWORD,users.password)
        values.put(COLUMN_USER_ADDRESS,users.address)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }*/
    fun addUser(users: Users){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME,users.name)
        values.put(COLUMN_USER_EMAIL,users.email)
        values.put(COLUMN_USER_PASSWORD,users.password)
        values.put(COLUMN_USER_ADDRESS,users.address)
        values.put(COLUMN_USER_IMAGE,users.image)
        db.insert(TABLE_NAME, null,values)
        db.close()

    }

    fun checkUser(email:String):Boolean{
        //it specifies the array of columns to fetch
        val columns  = arrayOf(COLUMN_USER_ID)
        val db  = this.readableDatabase

        // write the selection criteria

        val selecTion = "$COLUMN_USER_EMAIL = ?"

        // selection Arguments(the code that will perform the search)

        val selectionargs = arrayOf(email)

        val cursor = db.query(TABLE_NAME,
            columns,
            selecTion,
            selectionargs,
            null,
            null,
            null)
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount>0){
            return true
        }
            return false
    }

        fun checkUser(email: String,password:String): Boolean{
            // create array of columns to fectch from
            val columns = arrayOf(COLUMN_USER_ID)

            val db =this.readableDatabase

            //selection criteria
             val selecTion = "$COLUMN_USER_EMAIL=? AND $COLUMN_USER_PASSWORD = ?"

            // selection Arguments
            val selectionargs = arrayOf(email,password)

            //query user table with conditions
            //the query function is used to fetched records from the user table
            val cursor = db.query(TABLE_NAME,
                columns,
                selecTion,
                selectionargs,
                null,
                null,
                null)

            val cursorCount = cursor.count
            cursor.close()
            db.close()
            if (cursorCount>0){
                return true
            }

                return false

        }


    fun deleteUser(users: Users){
            val db = this.writableDatabase
            //delete user records by id
            db.delete(TABLE_NAME,"$COLUMN_USER_ID = ?",
                arrayOf(users.id.toString()))
        }

    fun updateUser(users: Users){

        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_USER_NAME,users.name)
        values.put(COLUMN_USER_EMAIL,users.email)
        values.put(COLUMN_USER_PASSWORD,users.password)
        values.put(COLUMN_USER_ADDRESS,users.address)
        values.put(COLUMN_USER_IMAGE,users.image)
        db.update(
            TABLE_NAME, values,"$COLUMN_USER_ID = ?",
            arrayOf(users.id.toString()))
            db.close()
    }

    fun fetchUsers():List<Users>{

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL,
            COLUMN_USER_PASSWORD, COLUMN_USER_ADDRESS, COLUMN_USER_IMAGE)

        // sorting order
        val sortOder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<Users>()

        val db = this.readableDatabase

        // query the user table

         val cursor = db.query(TABLE_NAME,
             columns,
             null,
             null,
             null,
             null,
             sortOder)
        if (cursor.moveToFirst()){
            do {
                val user = Users(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    address = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)),
                    image = cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_IMAGE)))

                userList.add(user)

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return  userList
    }

    companion object {
        private  val DATABASE_VERSION = 3

        private val DATABASE_NAME = "UsersDB.db"

        private val  TABLE_NAME = "users"

        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
        private val COLUMN_USER_ADDRESS =  "user_address"
        private val COLUMN_USER_IMAGE   =  "user_image"

    }

}

