package com.example.pruebareto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebareto.repository.UserRepository
import com.google.firebase.FirebaseApp

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var rememberMeCheckBox: CheckBox

    private val userRepository = UserRepository()

    //  SharedPreferences se usa para guardar los datos
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("User", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)
        rememberMeCheckBox = findViewById(R.id.remember_me_checkbox)


        checkBoxRememberUser()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            userRepository.loginUser(email, password) { success ->
                if (success) {
                    if (rememberMeCheckBox.isChecked) {
                        saveCredentials(email, password)
                    } else {
                        deleteCredentials()
                    }
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, WorkoutsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }

            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveCredentials(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }


    private fun deleteCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()
    }


    private fun checkBoxRememberUser() {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)

        if (email != null && password != null) {
            emailEditText.setText(email)
            passwordEditText.setText(password)
            rememberMeCheckBox.isChecked = true // Marcar el checkbox si hay credenciales guardadas
        }
    }
}
