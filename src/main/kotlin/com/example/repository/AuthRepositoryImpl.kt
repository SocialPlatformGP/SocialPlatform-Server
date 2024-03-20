package com.example.repository

import com.example.data.models.User
import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.client.gridfs.model.GridFSFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.KMongo

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.io.File
import java.io.InputStream

class AuthRepositoryImpl(db: CoroutineDatabase) : AuthRepository {
    private val users = db.getCollection<User>()
    override suspend fun createUser(user: User): User? {
        val success = users.insertOne(user).wasAcknowledged()
        if (!success) throw Exception("Could not create user")
        else return users.findOne(User::email eq user.email)
    }
    override suspend fun findUserByEmail(email: String): User? =
        users.findOne(User::email eq email)

    override suspend fun findUserById(id: String): User? =
        users.findOne(User::id eq id)

}
data class File(val name:String,val stream:InputStream)