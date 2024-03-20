package com.example.repository

import com.example.data.models.MaterialFile
import com.example.data.models.MaterialFolder
import com.example.data.responses.MaterialResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MaterialRepositoryImpl(
    private val db : CoroutineDatabase

) : MaterialRepository {
    private val materialFolders = db.getCollection<MaterialFolder>()
    private val materialFiles = db.getCollection<MaterialFile>()
    override suspend fun createMaterialFolder(
        materialFolder: MaterialFolder
    ): MaterialResponse {
       val success =  materialFolders.insertOne(materialFolder).wasAcknowledged()
        return if(success){
            val files = materialFiles.find(MaterialFile::path eq materialFolder.path).toList()
            val folders = materialFolders.find(MaterialFolder::path eq materialFolder.path).toList()
            MaterialResponse(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse()
        }
    }

    override suspend fun createMaterialFile(materialFile: MaterialFile): MaterialResponse {
        val success =  materialFiles.insertOne(materialFile).wasAcknowledged()
        return if(success){
            val files = materialFiles.find(MaterialFile::path eq materialFile.path).toList()
            val folders = materialFolders.find(MaterialFolder::path eq materialFile.path).toList()
            MaterialResponse(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse()
        }
    }

    override suspend fun getMaterialResponse(path: String): MaterialResponse {

        val materialFile = materialFiles.find(MaterialFile::path eq path).toList()
        val materialFolder = materialFolders.find(MaterialFolder::path eq path).toList()

        return MaterialResponse(
            materialFile.map { it.toResponse() },
            materialFolder.map { it.toResponse() }
        )
    }
}