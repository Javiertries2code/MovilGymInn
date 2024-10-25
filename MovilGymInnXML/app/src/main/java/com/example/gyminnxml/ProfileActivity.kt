package com.example.gyminnxml

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ProfileActivity :  AppCompatActivity() {
    //Lateinit lo que dice es que la variable va a ser inicializada mas tarde
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val btnVolverProfile = findViewById<Button>(R.id.buttonVolverProfile)
        btnVolverProfile.setOnClickListener {
            val intent = Intent(this, WorkOutsActivity::class.java)
            startActivity(intent)
            finish()
        }

        //PreferenceManager.getDefaultSharedPreferences(this) Estabamos usando este codigo pero
        // aparece como deprecated , asi que decidimos cambiar a el siguiente que nos crea un
        // archivo que solo sera accesible en nuestra aplicación
        sharedPreferences = getSharedPreferences("document_sharedPreferences", MODE_PRIVATE)

        val imageViewChangeLanguage: ImageView = findViewById(R.id.imageView4)
        val imageViewChangeTheme: ImageView = findViewById(R.id.imageView5)

        // Configura el listener para cambiar tema
        imageViewChangeTheme.setOnClickListener {
                    changeTheme()
        }
        // Configura el listener para cambiar el idioma
        imageViewChangeLanguage.setOnClickListener {
            
        }


    }

    private fun changeTheme() {
        // Obtiene el tema actual
        val currentTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_NO)

        // Cambia a tema dependiendo el tema que tenga
        val newTheme = if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        // Guarda el nuevo tema en SharedPreferences
        sharedPreferences.edit().putInt("theme", newTheme).apply()

        // Aplica el nuevo tema
        AppCompatDelegate.setDefaultNightMode(newTheme)
    }

    private fun changeLanguage(languageCode: String) {

    }
}