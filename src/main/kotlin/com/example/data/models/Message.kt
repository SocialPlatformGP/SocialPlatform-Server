package com.example.data.models

import de.undercouch.bson4jackson.types.Timestamp
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@kotlinx.serialization.Serializable
data class Message(
    val text :String,
    val userName:String,
    val timestamp: Long,
    @BsonId
    val id :String =ObjectId().toString()
)
