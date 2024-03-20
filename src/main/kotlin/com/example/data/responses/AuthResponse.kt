package com.example.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val errorMessage :String,
    val userId:String,
    val token:String
)
