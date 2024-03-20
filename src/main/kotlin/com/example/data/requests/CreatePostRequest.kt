package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val title: String="",
    val body: String="",
    val authorID: String="",
)
