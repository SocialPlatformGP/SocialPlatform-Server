package com.example

import com.example.plugins.*
import com.example.repository.AuthRepositoryImpl
import com.example.repository.AuthRepository
import com.example.repository.MaterialRepository
import com.example.repository.PostRepository
import com.example.room.RoomController
import com.example.security.JwtService
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {


    val hashingService: HashingService by inject()
    val authRepository: AuthRepository by inject()
    val postRepository: PostRepository by inject()
    val materialRepository: MaterialRepository by inject()
    val tokenService: TokenService by inject()
    val roomController: RoomController by inject()
    val tokenConfig = TokenConfig(
        issuer = "http://0.0.0.0:8080/",
        audience = "http://0.0.0.0:8080/hello",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = "secret"
    )
    configureDependencyInjection()
    configureSecurity(tokenConfig)
    configureMonitoring()
    configureSerialization()
    configureSession()
    configureSockets()

    configureRouting(
        hashingService = hashingService,
        authRepository = authRepository,
        postRepository = postRepository,
        tokenService = tokenService,
        tokenConfig = tokenConfig,
        roomController = roomController,
        materialRepository = materialRepository
    )
}
