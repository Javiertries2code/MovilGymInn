package com.example.gyminnxml.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gyminnxml.R

class WorkOutsActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workouts)

        val imagenProfile = findViewById<ImageView>(R.id.imageView3)
        imagenProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        val buttonVolver = findViewById<Button>(R.id.buttonVolverWorkouts)
        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnEntrenador = findViewById<Button>(R.id.trainerButtonWorkout)
        btnEntrenador.setOnClickListener {
            val intent = Intent(this, TrainerActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}
