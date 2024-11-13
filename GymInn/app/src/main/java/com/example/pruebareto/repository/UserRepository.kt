package com.example.pruebareto.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import android.util.Log
import com.example.pruebareto.User

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    fun saveUser(user: User, onComplete: (Boolean) -> Unit) {
        val userCollection = firestore.collection("users")

        userCollection.document(user.user).set(user)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("UserRepository", "Error saving user", e)
                onComplete(false)
            }
    }


    fun getUser(userId: String, onComplete: (User?) -> Unit) {
        val userCollection = firestore.collection("users")

        userCollection.document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    onComplete(user)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("UserRepository", "Error fetching user", e)
                onComplete(null)
            }
    }


    fun registerUser(email: String, password: String, user: User, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult: AuthResult ->
                val userId = authResult.user?.uid ?: ""
                user.user = userId
                saveUser(user) { success ->
                    onComplete(success)
                }
            }
            .addOnFailureListener { e ->
                Log.e("UserRepository", "Registration failed", e)
                onComplete(false)
            }
    }


    fun loginUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult: AuthResult ->
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("UserRepository", "Login failed", e)
                onComplete(false)
            }
    }



}
