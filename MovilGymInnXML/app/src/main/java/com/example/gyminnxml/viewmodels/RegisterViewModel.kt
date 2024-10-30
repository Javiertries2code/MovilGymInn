package com.example.gyminnxml.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gyminnxml.models.User
import com.example.gyminnxml.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestoreException

class RegisterViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel() {

    private val _registrationStatus = MutableLiveData<String>()
    val registrationStatus: LiveData<String> get() = _registrationStatus

    fun registerUser(user: User) {
        userRepository.checkIfUserExists(user.email).addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                userRepository.registerUser(user)
                    .addOnSuccessListener {
                        _registrationStatus.value = "Usuario registrado correctamente."
                    }
                    .addOnFailureListener { e ->
                        _registrationStatus.value = "Error al registrar: ${e.message}"
                    }
            } else {
                _registrationStatus.value = "Este usuario ya está registrado."
            }
        }.addOnFailureListener { e ->
            _registrationStatus.value = "Error al verificar usuario: ${e.message}"
        }
    }
}
