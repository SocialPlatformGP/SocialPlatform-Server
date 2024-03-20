package com.example.data.models

import com.example.data.responses.UserResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class User(

    @BsonId
    val id: String = ObjectId().toString(),
    val salt: String="",
    val isAdmin: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long=0L,
    val bio: String = "",
    @Contextual
    val createdAt: LocalDateTime = LocalDateTime.now(),
){
    fun toResponse() = UserResponse(
        id = id,
        isAdmin = isAdmin,
        firstName = firstName,
        lastName = lastName,
        profilePictureURL = profilePictureURL,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        bio = bio,
        createdAt = createdAt.toString()
    )
}