package com.example.data.responses

import com.example.data.models.User
@kotlinx.serialization.Serializable
data class PostResponse(
    val id: String="",
    val title: String="",
    val body: String="",
    val user: User= User(),
    val createdAt: String="",
    val updatedAt: String="",
    val deletedAt: String="",
    val deleted: Boolean=false,

)
