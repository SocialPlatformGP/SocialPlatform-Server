package com.example.data.models

import com.example.data.responses.PostResponse
import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class Post(
    val userId: String="",
    val replyCount: Int=0,
    val userName:String="",
    val userPfp:String="",
    val authorEmail: String="",
    val title: String="",
    val body: String="",
    val votes: Int=0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "all",
    val attachments: List<PostAttachment> = emptyList(),
    @Contextual val createdAt: LocalDateTime = LocalDateTime.now(),
    @Contextual val updatedAt: LocalDateTime? = null,
    @Contextual val deletedAt: LocalDateTime? = null,
    @Contextual
    @BsonId
    val id: String = ObjectId().toString(),

) {
    var user : User = User()
    fun toResponse() = PostResponse(
        id = id,
        title = title,
        body = body,
        tags = tags,
        attachments = attachments,
        publishedAt = createdAt.toString(),
        userName = userName,
        userPfp = userPfp,
        authorEmail = authorEmail,
        moderationStatus = moderationStatus,
        editStatus = editStatus,
        type = type,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        replyCount = replyCount


        )
}
@kotlinx.serialization.Serializable
data class Tag(
    val label: String="",
    val hexColor: String="#000000",
)
@kotlinx.serialization.Serializable
data class PostAttachment(
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)