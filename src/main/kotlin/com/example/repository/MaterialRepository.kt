package com.example.repository

import com.example.data.models.MaterialFile
import com.example.data.models.MaterialFolder
import com.example.data.responses.MaterialResponse

interface MaterialRepository {
    suspend fun createMaterialFolder(
        MaterialFolder: MaterialFolder
    ):MaterialResponse

    suspend fun createMaterialFile(
        MaterialFile: MaterialFile
    ):MaterialResponse
    suspend fun getMaterialResponse(
        path: String
    ):MaterialResponse
}