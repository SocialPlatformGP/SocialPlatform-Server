package com.example.data.responses
@kotlinx.serialization.Serializable
data class TagResponse(
    val label: String="",
    val color: Int=0,
    val hex : String=""
)

