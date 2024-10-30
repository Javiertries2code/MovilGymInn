package com.example.gyminnxml.models

data class User(
    val name: String = "",
    val surname: String = "",
    val date: String = "",
    val email: String = "",
    val user: String = "",
    val password: String = "",
    val level: Int = 0,
    val progress: Int = 0,
    val registered: Boolean = true,
    val ref_workouts: List<String>? = null,
    val wk_history: List<String>? = null,
    val isTrainer: Boolean = false
)
