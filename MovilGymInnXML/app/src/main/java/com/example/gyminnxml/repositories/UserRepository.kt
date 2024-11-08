package com.example.gyminnxml.repositories

import com.example.gyminnxml.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class UserRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    fun checkIfUserExists(email: String): Task<QuerySnapshot> {
        return db.collection("usuarios").whereEqualTo("email", email).get()
    }

    fun registerUser(user: User): Task<Void> {
        val userData = hashMapOf(
            "name" to user.name,
            "surname" to user.surname,
            "date" to user.date,
            "email" to user.email,
            "user" to user.user,
            "password" to user.password,
            "level" to user.level,
            "progress" to user.progress,
            "registered" to user.registered,
            "ref_workouts" to user.ref_workouts,
            "wk_history" to user.wk_history,
            "isTrainer" to user.isTrainer
        )
        return db.collection("usuarios").add(userData).continueWith { null }
    }
    // Método para validar las credenciales de un usuario (para el login)
    fun validateCredentials(email: String, password: String): Task<QuerySnapshot> {
        return db.collection("usuarios")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
    }


}
