package com.example.di

import com.example.repository.*
import com.example.room.RoomController
import com.example.security.JwtService
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SHA256HashingService
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single<MaterialRepository>{ MaterialRepositoryImpl(get()) }
    single<PostRepository>{ PostRepositoryImpl(get()) }
    single<HashingService>{ SHA256HashingService() }
    single<MessageDataSource>{ MessageDataSourceImpl(get()) }
    single<TokenService>{ JwtService() }
    single {
        RoomController(get())
    }

    single {
        KMongo.createClient().coroutine.getDatabase("EduLink_db")
    }
}