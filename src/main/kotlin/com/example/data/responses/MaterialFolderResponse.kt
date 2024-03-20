package com.example.data.responses

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFileResponse(
    val name: String="",
    val type: String="",
    val url: String="",
    val createdAt: LocalDateTime? = null,
    val id : String ="",
    val path : String = "",
)
@Serializable
data class MaterialFolderResponse(
    val name: String="",
    val createdAt: LocalDateTime? = null,
    val id : String = "",
    val path: String = ""
)
@Serializable
data class MaterialResponse(
    val files: List<MaterialFileResponse> = emptyList(),
    val folders: List<MaterialFolderResponse> = emptyList()
)