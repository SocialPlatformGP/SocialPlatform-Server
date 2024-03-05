package com.example.repository

import com.example.data.models.User

interface AuthRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun findUserByEmail(email:String): User?
}