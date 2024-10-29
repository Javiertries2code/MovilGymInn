package com.example.gyminnxml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class TrainerActivity :  AppCompatActivity() {
    private lateinit var  database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer)

        val workoutList = findViewById<RecyclerView>(R.id.workoutList)

        val buttonVolver = findViewById<Button>(R.id.backButtonTrainer)
        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}