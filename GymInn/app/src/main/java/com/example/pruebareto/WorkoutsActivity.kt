package com.example.pruebareto

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class WorkoutsActivity : AppCompatActivity() {
    private lateinit var profileButton: ImageView
    private lateinit var returnButton: Button
    private lateinit var  trainerButtonWorkout:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        profileButton = findViewById(R.id.profile_button)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        returnButton = findViewById(R.id.buttonVolverWorkouts)
        returnButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        trainerButtonWorkout = findViewById(R.id.trainerButtonWorkout)
        trainerButtonWorkout.setOnClickListener {
            val intent = Intent(this, TrainerActivity::class.java)
            startActivity(intent)
        }
        }

}