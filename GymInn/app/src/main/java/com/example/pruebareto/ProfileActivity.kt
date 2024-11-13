package com.example.pruebareto

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        val user = auth.currentUser
        if (user != null) {
            userInformation(user.uid)
        }


        val darkModeButton = findViewById<Button>(R.id.dark_mode_button)
        darkModeButton.setOnClickListener {
            changeTheme()
        }

        // Cambiar el idioma
        val changeLanguageButton = findViewById<Button>(R.id.change_language_button)
        changeLanguageButton.setOnClickListener {
            changeLanguage()
        }

        val return_profile_button =findViewById<Button>(R.id.return_profile_button)
        return_profile_button.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun userInformation(userId: String) {
        val nameText = findViewById<TextView>(R.id.name_text)
        val lastNameText = findViewById<TextView>(R.id.last_name_text)
        val emailText = findViewById<TextView>(R.id.email_text)
        val birthdayText = findViewById<TextView>(R.id.birthdate_text)

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name")
                    val lastName = document.getString("lastName")
                    val email = document.getString("email")
                    val birthDate = document.getString("birthDate")

                    nameText.text = name
                    lastNameText.text = lastName
                    emailText.text = email
                    birthdayText.text =birthDate
                }
            }
    }

    private fun changeTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode", currentMode != AppCompatDelegate.MODE_NIGHT_YES)
        editor.apply()
    }

    private fun changeLanguage() {

    }
}