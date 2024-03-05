package com.example.plugins

import com.example.repository.AuthRepository
import com.example.routes.authenticate
import com.example.routes.home
import com.example.routes.signIn
import com.example.routes.signUp
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    authRepository: AuthRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {

    routing {
        signUp(
            hashingService = hashingService,
            authRepository = authRepository
        )

        signIn(
            authRepository = authRepository,
            tokenService = tokenService,
            hashingService = hashingService,
            tokenConfig = tokenConfig
        )
        authenticate()
        home()
    }
}
