package com.example.gyminnxml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gyminnxml.models.User
import com.example.gyminnxml.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName = findViewById<EditText>(R.id.textInputEditText2)
        val editTextSurname = findViewById<EditText>(R.id.editText)
        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val editTextEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val editTextuser = findViewById<EditText>(R.id.editTextUsername)

        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val checkBoxTrainer = findViewById<CheckBox>(R.id.checkBox)



        setupObservers()
        val btnReturn = findViewById<TextView>(R.id.ButtonVolver)
        btnReturn.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val btnRegistro = findViewById<Button>(R.id.buttonRegistro)
        btnRegistro.setOnClickListener {
            val user = User(
                name = editTextName.text.toString().trim(),
                surname = editTextSurname.text.toString().trim(),
                date = editTextDate.text.toString().trim(),
                email = editTextEmail.text.toString().trim(),
                user = editTextuser.text.toString().trim(),
                password = editTextPassword.text.toString().trim(),
                isTrainer = checkBoxTrainer.isChecked
            )
            registerViewModel.registerUser(user)
        }

    }

    private fun setupObservers() {
        registerViewModel.registrationStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })
    }
}