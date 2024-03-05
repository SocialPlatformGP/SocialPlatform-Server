package com.example.data.models

import com.example.data.responses.PostResponse
import de.undercouch.bson4jackson.types.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDate
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class Post(
    val title: String,
    val body: String,
    val userId: String,

    @Contextual val createdAt: LocalDateTime = LocalDateTime.now(),
    @Contextual val updatedAt: LocalDateTime? = null,
    @Contextual val deletedAt: LocalDateTime? = null,
    @Contextual
    @BsonId
    val id: String = ObjectId().toString(),

) {
    var user : User = User()
    fun toResponse(user: User) = PostResponse(
        id = id.toString(),
        title = title,
        body = body,
        user = user,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt?.toString() ?: "",
        deletedAt = deletedAt?.toString() ?: "",

        )
}