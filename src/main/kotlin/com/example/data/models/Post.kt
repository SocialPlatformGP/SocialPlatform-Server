package com.example.data.models

import com.example.data.responses.PostResponse
import kotlinx.datetime.*

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.UTC)


@kotlinx.serialization.Serializable
data class Post(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val authorID: String = "",
    val createdAt: Long = kotlinx.datetime.LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editedStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "general",
    val attachments: List<PostFile> = emptyList(),
    val lastModified: Long = kotlinx.datetime.LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    @BsonId
    val id: String = ObjectId().toString(),

    ) {
    var user: User = User()
    fun toResponse() = PostResponse(
        id = id,
        title = title,
        body = body,
        tags = tags,
        attachments = attachments,
        createdAt = Instant.fromEpochSeconds(createdAt).toLocalDateTime(TimeZone.UTC),
        authorName = authorName,
        authorPfp = authorPfp,
        authorID = authorID,
        moderationStatus = moderationStatus,
        editedStatus = editedStatus,
        type = type,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        replyCount = replyCount,
        lastModified = Instant.fromEpochSeconds(lastModified).toLocalDateTime(TimeZone.UTC)


    )
}

@kotlinx.serialization.Serializable
data class Tag(
    @BsonId
    val id: String = ObjectId().toString(),
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
)

@kotlinx.serialization.Serializable
data class PostFile(
    val file: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)



