package com.example.plugins

import com.example.repository.AuthRepository
import com.example.repository.MaterialRepository
import com.example.repository.PostRepository
import com.example.room.RoomController
import com.example.routes.*
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    authRepository: AuthRepository,
    postRepository: PostRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    roomController: RoomController,
    materialRepository: MaterialRepository
) {

    routing {
        signUp(
            hashingService = hashingService,
            authRepository = authRepository,
            tokenService = tokenService,
            tokenConfig = tokenConfig
        )

        signIn(
            authRepository = authRepository,
            tokenService = tokenService,
            hashingService = hashingService,
            tokenConfig = tokenConfig
        )
        createPost(
            postRepository = postRepository
        )
        getAllPosts(
            postRepository = postRepository
        )
        upVotePost(
            postRepository = postRepository
        )
        downVotePost(
            postRepository = postRepository
        )
        deletePost(
            postRepository = postRepository
        )
        updatePost(
            postRepository = postRepository
        )
        getSignedUser(
            authRepository = authRepository
        )
        home()
        getAllMessages(roomController)
        chatRoute(roomController)
        filesRoute(
            materialRepository = materialRepository
        )
    }

}
