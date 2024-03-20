package com.example.data.requests
@kotlinx.serialization.Serializable
data class AddTagRequest(
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
){
    fun toEntity() = com.example.data.models.Tag(
        label = label,
        intColor = intColor,
        hexColor = hexColor
    )
}
