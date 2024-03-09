package com.example.repository

import com.example.data.models.Post
import com.example.data.requests.CreatePostRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse

interface PostRepository {
    suspend fun createPost(postRequest: PostResponse): Boolean
    suspend fun updatePost(postRequest: PostResponse): Boolean
    suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean
    suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun getPosts(): List<PostResponse>
    suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): Boolean
}