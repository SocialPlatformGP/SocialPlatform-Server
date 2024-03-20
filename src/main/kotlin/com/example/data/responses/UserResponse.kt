package com.example.data.responses

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class UserResponse(
    val id: String = "",
    val isAdmin: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long=0L,
    val bio: String = "",
    val createdAt: String = "",
)