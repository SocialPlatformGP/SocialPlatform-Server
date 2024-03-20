package com.example.repository

import com.example.data.models.User
import com.mongodb.client.gridfs.model.GridFSFile
import java.io.InputStream

interface AuthRepository {
    suspend fun createUser(user: User): User?
    suspend fun findUserByEmail(email:String): User?
    suspend fun findUserById(id:String): User?
}