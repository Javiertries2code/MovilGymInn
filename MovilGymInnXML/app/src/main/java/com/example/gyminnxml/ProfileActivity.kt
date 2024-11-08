package com.example.gyminnxml

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity :  AppCompatActivity() {
    //Lateinit lo que dice es que la variable va a ser inicializada mas tarde
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var userTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userBirthdayTextView: TextView
    private lateinit var userNameTextView: TextView
    private lateinit var userSurnameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializa los TextViews
        userTextView = findViewById(R.id.editTextText)
        userNameTextView = findViewById(R.id.editTextText3)
        userSurnameTextView = findViewById(R.id.editTextText2)
        userEmailTextView = findViewById(R.id.editTextTextEmailAddress2)
        userBirthdayTextView = findViewById(R.id.editTextDate2)


        // Recibir los datos del Intent
        val userName = intent.getStringExtra("user_name")
        val userEmail = intent.getStringExtra("user_email")
        val userBirthday = intent.getStringExtra("user_birthday")
        val userPhone = intent.getStringExtra("user_phone")

        // Mostrar los datos en la interfaz
        userTextView.text = userName ?: "No disponible"
        userNameTextView.text = userPhone ?: "No disponible"
        userSurnameTextView.text = userPhone ?: "No disponible"
        userEmailTextView.text = userEmail ?: "No disponible"
        userBirthdayTextView.text = userBirthday ?: "No disponible"

    }
}



/* // Cargar el idioma almacenado
 sharedPreferences = getSharedPreferences("document_sharedPreferences", MODE_PRIVATE)
 val language = sharedPreferences.getString("language", "en") // "es" por defecto
 setLocale(language ?: "en")

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


 val spinnerChangeLanguage: Spinner = findViewById(R.id.spinner)
 val imageViewChangeTheme: ImageView = findViewById(R.id.imageView5)

 // Configura el listener para cambiar tema
 imageViewChangeTheme.setOnClickListener {
     changeTheme()
 }

 // Inicializa el Spinner con opciones
 val languages = arrayOf(getString(R.string.spanish), getString(R.string.english))
 val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
 spinnerChangeLanguage.adapter = adapter

 // Configura el listener para cambiar el idioma
 spinnerChangeLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

     override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
         val selectedLanguage = when (position) {
             0 -> "es" // Español
             1 -> "en" // Inglés
             else -> "es"
         }

         changeLanguage(selectedLanguage)

     }
     override fun onNothingSelected(parent: AdapterView<*>) {
     }
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
 // Guarda el idioma seleccionado en SharedPreferences
 sharedPreferences.edit().putString("language", languageCode).apply()

 // Cambia el idioma de la aplicación
 val locale = Locale(languageCode)
 Locale.setDefault(locale)

 // Crear una nueva configuración con el nuevo idioma
 val config = Configuration(resources.configuration)
 config.setLocale(locale)

 // Actualiza la configuración de recursos
 resources.updateConfiguration(config, resources.displayMetrics)


}

private fun setLocale(lang: String) {
 val locale = Locale(lang)
 Locale.setDefault(locale)

 // Crear una nueva configuración con el nuevo idioma
 val config = Configuration(resources.configuration)
 config.setLocale(locale)

 // Actualiza la configuración de recursos
 resources.updateConfiguration(config, resources.displayMetrics)

 // Reinicia la actividad para aplicar los cambios
 recreate()
}

*/
