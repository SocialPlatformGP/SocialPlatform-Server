package com.example.repository

import com.example.data.models.User
import com.example.repository.AuthRepository
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class AuthRepositoryImpl(db: CoroutineDatabase) : AuthRepository {
    private val users = db.getCollection<User>()
    override suspend fun findUserByUsername(username: String): User? =
        users.findOne(User::username eq username)
    override suspend fun createUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()
    override suspend fun findUserByEmail(email: String): User? =
        users.findOne(User::email eq email)
}