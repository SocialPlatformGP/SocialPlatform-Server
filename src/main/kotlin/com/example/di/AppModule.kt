package com.example.di

import com.example.repository.AuthRepositoryImpl
import com.example.repository.AuthRepository
import com.example.security.JwtService
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SHA256HashingService
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single<HashingService>{ SHA256HashingService() }
    single<TokenService>{ JwtService() }

    single {
        KMongo.createClient().coroutine.getDatabase("EduLink_db")
    }
}