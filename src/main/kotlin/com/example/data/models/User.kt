package com.example.data.models

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
    val userFirstName: String="",
    val userLastName: String="",
    val userPassword: String="",
    val userProfilePictureURL: String="",
    val userEmail: String="",
    val userPhoneNumber: String="",
    val userBirthdate: String="",
    val userBio: String="",
    @Contextual val userCreatedAt: LocalDateTime = LocalDateTime.now(),
    val administration: Boolean=false,
)