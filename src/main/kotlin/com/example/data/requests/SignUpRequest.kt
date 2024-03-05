package com.example.data.requests


import kotlinx.serialization.Serializable


@Serializable
data class SignUpRequest(
    val userFirstName: String="",
    val userLastName: String="",
    val userPassword: String="",
    val userProfilePictureURL: String="",
    val userEmail: String="",
    val userPhoneNumber: String="",
    val userBirthdate: String="",
    val userBio: String="",
)
