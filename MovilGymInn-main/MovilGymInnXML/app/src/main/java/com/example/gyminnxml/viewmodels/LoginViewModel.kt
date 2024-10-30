package com.example.gyminnxml.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gyminnxml.repositories.UserRepository

class LoginViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel() {

    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    fun loginUser(email: String, password: String) {
        userRepository.validateCredentials(email, password)
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    _loginStatus.value = "Usuario o contraseña incorrectos."
                } else {
                    _loginStatus.value = "Login exitoso"
                }
            }
            .addOnFailureListener { e ->
                _loginStatus.value = "Error al intentar iniciar sesión: ${e.message}"
            }
    }
}
