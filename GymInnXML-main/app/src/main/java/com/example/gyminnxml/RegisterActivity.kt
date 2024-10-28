package com.example.gyminnxml

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance()
        val editTextName = findViewById<EditText>(R.id.textInputEditText2)
        val editTextSurname = findViewById<EditText>(R.id.editText)
        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val editTextEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val editTextuser = findViewById<EditText>(R.id.editTextUsername)

        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val checkBoxTrainer = findViewById<CheckBox>(R.id.checkBox)

        // Recupera el estado del CheckBox de SharedPreferences
        val preferences = getSharedPreferences("GymAppPrefs", Context.MODE_PRIVATE)
        checkBoxTrainer.isChecked = preferences.getBoolean("isChecked", false)

        // Configura el listener del CheckBox
        checkBoxTrainer.setOnCheckedChangeListener { _, isChecked ->
            val editor = preferences.edit()
            editor.putBoolean("isChecked", isChecked)
            editor.apply()

        }
        // Botón de registro
        val btnRegistro = findViewById<Button>(R.id.buttonRegistro)
        btnRegistro.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val surname = editTextSurname.text.toString().trim()
            val date = editTextDate.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val user = editTextuser.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val isTrainer = checkBoxTrainer.isChecked

            // Verificar que todos los campos estén llenos
            if (name.isEmpty() || surname.isEmpty() || date.isEmpty() || email.isEmpty() ||user.isEmpty()|| password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si el usuario ya existe
            db.collection("usuarios").whereEqualTo("user", user)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // Mensaje de error si el usuario ya existe
                        Toast.makeText(this, "Este usuario ya está registrado.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Crear nuevo usuario en la base de datos
                        val user = hashMapOf(
                            "name" to name,
                            "surname" to surname,
                            "date" to date,
                            "email" to email,
                            "user" to user,
                            "password" to password,
                            "isTrainer" to isTrainer
                        )

                        db.collection("usuarios").add(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show()
                                // Redirigir al MainActivity
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al verificar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Botón de volver
        val btnVolver = findViewById<TextView>(R.id.ButtonVolver)
        btnVolver.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
