package com.example.data.responses

import com.example.data.models.Post
import com.example.data.models.PostAttachment
import com.example.data.models.Tag
import com.example.data.models.User
import kotlinx.serialization.Contextual
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class PostResponse(
    val replyCount: Int=0,
    val userName:String="",
    val userPfp:String="",
    val id: String="",
    val authorEmail: String="",
     val publishedAt: String="",
    val title: String="",
    val body: String="",
    val votes: Int=0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "all",
    val attachments: List<PostAttachment> = emptyList()
    ){
    fun toEntity() = Post(
        userId = "",
        replyCount = replyCount,
        userName = userName,
        userPfp = userPfp,
        authorEmail = authorEmail,
        title = title,
        body = body,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        moderationStatus = moderationStatus,
        editStatus = editStatus,
        tags = tags,
        type = type,
        attachments = attachments
    )
}
