package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"]?: "unknown"
            call.sessions.set(ChatSession(username, generateNonce()))
        }
    }
}
data class ChatSession(
    val userName: String,
    val sessionId :String
    )
