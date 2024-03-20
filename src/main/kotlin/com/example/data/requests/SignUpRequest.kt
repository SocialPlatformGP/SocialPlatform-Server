package com.example.data.requests


import kotlinx.serialization.Serializable


@Serializable
data class SignUpRequest(
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long=0L,
    val bio: String = "",
    val isAdmin: Boolean = false,
)
