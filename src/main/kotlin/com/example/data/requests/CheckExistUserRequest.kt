package com.example.data.requests
import kotlinx.serialization.Serializable
@Serializable
data class CheckExistUserRequest(
    val email: String = "" ,
    val password: String = ""
)
