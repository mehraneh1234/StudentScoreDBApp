package com.example.studentscoredbapp
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
// Mehraneh - 30062786 - AT1-Activity6
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "student_management"
        private val DB_VERSION = 1
        val TABLE_NAME = "student_score"
        val ID = "Id"
        val SUBJECT = "Subject"
        val SCORE = "Score"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$ID INTEGER PRIMARY KEY," +
                        "$SUBJECT TEXT," +
                        "$SCORE TEXT" + ")"
                )
        db?.execSQL(query) // nullable
    }
    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // non-null assertion.
        //Error if null at compile time
        onCreate(db)
    }

    // This method is to add a record in DB
    fun addSubject(subject: String, score: String) {
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        // insert key-value pairs
        values.put(SUBJECT, subject)
        values.put(SCORE, score)
        // create a writable DB variable of our database to insert record
        val db = this.writableDatabase
        // insert all values into DB
        db.insert(TABLE_NAME, null, values)
        // close DB
        db.close()
    }
    // This method is get all Subjects records from DB
    fun getAllSubjects(): Cursor? {
        // create a readable DB variable of our database to read record
        val db = this.readableDatabase
        // read all records from DB
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }
    fun deleteSubject(subject: String): Int {
        // create a writable DB variable of our database to delete record
        val db = this.writableDatabase
        // delete a user by NAME
        val rows = db.delete(TABLE_NAME, "Subject=?", arrayOf(subject))
        db.close();
        return rows // 0 or 1
    }
    fun updateSubject(subject: String, score: String): Int {
        // create a writable DB variable of our database to update record
        val db = this.writableDatabase
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        values.put(SCORE, score)
        val rows = db.update(TABLE_NAME, values, "Subject=?", arrayOf(subject))
        db.close()
        return rows // rows updated
    }

}