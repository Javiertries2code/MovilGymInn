package com.example.gyminnxml.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gyminnxml.R
import com.example.gyminnxml.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {
    //HOLAAs
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var checkBoxRemember: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.etUsuario)
        editTextPassword = findViewById(R.id.editTextTextPassword2)
        checkBoxRemember = findViewById(R.id.rememberMe)
        sharedPreferences = getSharedPreferences("GymInnPrefs", Context.MODE_PRIVATE)


        setupObservers()
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            loginViewModel.loginUser(email, password)
        }

        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun setupObservers() {
        loginViewModel.loginStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
            if (status == "Login exitoso") {
                saveUserIfRemembered()
                startActivity(Intent(this, WorkOutsActivity::class.java))
                finish()
            }
        })
    }

    private fun saveUserIfRemembered() {
        val editor = sharedPreferences.edit()
        if (checkBoxRemember.isChecked) {
            editor.putString("last_user_email", editTextEmail.text.toString().trim())
            editor.putString("last_user_password", editTextPassword.text.toString().trim())
        } else {
            // Eliminamos las credenciales guardadas si el checkbox no está marcado
            editor.remove("last_user_email")
            editor.remove("last_user_password")
        }
        editor.apply()
    }


    private fun loadLastUser() {
        val lastUserEmail = sharedPreferences.getString("last_user_email", null)
        val lastUserPassword = sharedPreferences.getString("last_user_password", null)
        if (!lastUserEmail.isNullOrEmpty() && !lastUserPassword.isNullOrEmpty()) {
            editTextEmail.setText(lastUserEmail)
            editTextPassword.setText(lastUserPassword)
            checkBoxRemember.isChecked = true
        }
    }
}
