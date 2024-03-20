package com.example.repository

import com.example.data.models.Post
import com.example.data.models.Tag
import com.example.data.requests.CreatePostRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import kotlinx.datetime.LocalDateTime

interface PostRepository {
    suspend fun createPost(postRequest: PostResponse): Boolean
    suspend fun updatePost(postRequest: PostResponse): Boolean
    suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean
    suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun getPosts(): List<PostResponse>
    suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): Boolean

    suspend fun insertTag(tag: Tag): Boolean
    suspend fun getTags(): List<Tag>
    suspend fun getNewPosts(request: LocalDateTime): List<PostResponse>
}