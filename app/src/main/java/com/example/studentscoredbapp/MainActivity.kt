package com.example.studentscoredbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

// Mehraneh - 30062786 - AT1-Activity6
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // By click on Add button The entry data in Subject and Score fields send to addSubject
        // method then the controllers subject and score will be cleared.
        val btnAddSub = findViewById<Button>(R.id.btnAddSubject)
        btnAddSub.setOnClickListener {
            val db = DBHelper(this, null)
            val etSubject = findViewById<EditText>(R.id.etSubject)
            val etScore = findViewById<EditText>(R.id.etScore)
            val subject = etSubject.text.toString()
            val score = etScore.text.toString()
            db.addSubject(subject, score)
            // Toast to message on the screen
            Toast.makeText(this, subject + " added to database", Toast.LENGTH_SHORT).show()
            etSubject.text.clear()
            etScore.text.clear()
        }
        // By clicking on the print subjects, the getAllSubject method calls the display all records
        // in the text view
        val btnPrintSubjects = findViewById<Button>(R.id.btnPrintSubjects)
        btnPrintSubjects.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getAllSubjects()
            cursor!!.moveToFirst()
            val tvSubjectRecord = findViewById<TextView>(R.id.tvSubjectRecord)
            tvSubjectRecord.text = "### Subjects ###\n"
            if (cursor!!.moveToFirst()) {
                tvSubjectRecord.append(cursor.getString(0) + ": " +
                        cursor.getString(1) +
                        "(" + cursor.getString(2) + ")\n")
            }
            while (cursor.moveToNext()) {
                tvSubjectRecord.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)) +
                        ": " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SUBJECT)) +
                        "(" + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SCORE)) + ")\n")
            }
        }
        // By clicking on the DeleteSubject button, if the subject is written in the subject
        // controller, that record is deleted.
        val btnDeleteSubject = findViewById<Button>(R.id.btnDeleteSubject)
        btnDeleteSubject.setOnClickListener {
            val db = DBHelper(this, null)
            val subject = findViewById<EditText>(R.id.etSubject).text.toString()
            val rows = db.deleteSubject(subject)
            Toast.makeText(this,
                when (rows) {
                    0 -> "Nothing deleted"
                    1 -> "1 record deleted"
                    else -> "" // shouldn't happen
                },
                Toast.LENGTH_LONG).show()
        }
        // By clicking on the update score button, regarding to the subject that is written in the
        //subject controller the score is changed to the new one that is written in score controller
        val btnUpdateScore = findViewById<Button>(R.id.btnUpdateScore)
        btnUpdateScore.setOnClickListener {
            val db = DBHelper(this, null)
            val subject = findViewById<EditText>(R.id.etSubject).text.toString()
            val score = findViewById<EditText>(R.id.etScore).text.toString()
            val rows = db.updateSubject(subject, score)
            Toast.makeText(this, "$rows subjects updated", Toast.LENGTH_LONG).show()
        }

    }
}