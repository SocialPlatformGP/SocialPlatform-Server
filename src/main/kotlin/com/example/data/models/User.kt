package com.example.data.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@kotlinx.serialization.Serializable
data class User(
    val username: String="",
    val password: String="",
    val email:String="",
    val salt: String="",
    @BsonId
    val id: String = ObjectId().toString()
)
