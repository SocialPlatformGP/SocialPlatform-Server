package com.example.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class IsEmailAvailableResponse(
    val isAvailable: Boolean,
    val message: String,
)
