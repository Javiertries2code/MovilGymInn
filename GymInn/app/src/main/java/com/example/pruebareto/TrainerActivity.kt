package com.example.pruebareto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TrainerActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer)

        val buttonVolver = findViewById<Button>(R.id.backButtonTrainer)
        buttonVolver.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)



        }



    }
}