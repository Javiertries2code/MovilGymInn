package com.example.gyminnxml

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences // Para guardar preferencias del usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar la instancia de Firestore
        val db = FirebaseFirestore.getInstance()

        // Inicializar SharedPreferences para guardar los datos del usuario
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Obtener referencias a los campos de entrada
        val nameField = findViewById<EditText>(R.id.etUsuario)
        val passwordField = findViewById<EditText>(R.id.etPassword)

        // Botón para ir a la actividad de registro
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Termina la actividad actual
        }

        // Obtener referencia al checkbox de "Recordar mis datos"
        val rememberMeCheckbox = findViewById<CheckBox>(R.id.rememberMe)

        // Cargar datos guardados al iniciar la actividad
        loadUserData(nameField, passwordField, rememberMeCheckbox)

        // Botón para iniciar sesión
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val nameInput = nameField.text.toString().trim() // Obtener el nombre ingresado
            val password = passwordField.text.toString().trim() // Obtener la contraseña ingresada

            // Verificar que ambos campos no estén vacíos
            if (nameInput.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa tus datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir si hay campos vacíos
            }

            // Consultar Firestore para obtener documentos con la contraseña proporcionada
            db.collection("usuarios")
                .whereEqualTo("password", password) // Filtrar por contraseña
                .get()
                .addOnSuccessListener { documents ->
                    // Verificar si se encontró al menos un documento
                    if (documents.isEmpty) {
                        Toast.makeText(this, "El usuario inexistente", Toast.LENGTH_SHORT).show()
                    } else {
                        // Obtener el nombre del primer documento encontrado
                        val dbName = documents.first().getString("user")?.lowercase() // Convertir a minúsculas el nombre encontrado que corresponde a la contraseña proporcionada

                        // Comparar el nombre ingresado con el nombre de la base de datos (en minúsculas)
                        if (dbName == nameInput.lowercase()) {
                            // Guardar datos si el checkbox está marcado
                            if (rememberMeCheckbox.isChecked) {
                                saveUserData(nameInput, password) // Guardar nombre y contraseña
                            } else {
                                clearUserData() // Limpiar datos si el checkbox no está marcado
                            }
                            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, WorkOutsActivity::class.java)
                            startActivity(intent) // Ir a la actividad de WorkOuts
                            finish() // Terminar la actividad actual
                        } else {
                            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error en el inicio de sesión: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Función para guardar el nombre de usuario y la contraseña en SharedPreferences
    private fun saveUserData(name: String, password: String) {
        val editor = sharedPreferences.edit() // Obtener editor para modificar SharedPreferences
        editor.putString("username", name) // Guardar nombre de usuario
        editor.putString("password", password) // Guardar contraseña
        editor.apply() // Aplicar los cambios
    }

    // Función para limpiar los datos guardados en SharedPreferences
    private fun clearUserData() {
        val editor = sharedPreferences.edit() // Obtener editor para modificar SharedPreferences
        editor.clear() // Limpiar todos los datos guardados
        editor.apply() // Aplicar los cambios
    }

    // Función para cargar los datos del usuario desde SharedPreferences
    private fun loadUserData(nameField: EditText, passwordField: EditText, rememberMeCheckbox: CheckBox) {
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")


        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            nameField.setText(savedUsername) // Establecer el nombre en el campo de texto
            passwordField.setText(savedPassword) // Establecer la contraseña en el campo de texto
            rememberMeCheckbox.isChecked = true // Marcar el checkbox
        } else {
            // Limpiar los campos si no hay datos guardados
            nameField.text.clear() // Limpiar el campo de nombre
            passwordField.text.clear() // Limpiar el campo de contraseña
            rememberMeCheckbox.isChecked = false // Desmarcar el checkbox
        }
    }
}
