package com.example.routes

import com.example.data.requests.CreatePostRequest
import com.example.repository.PostRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPost(
    postRepository: PostRepository
){
    post("createPost"){
        val request = call.receiveNullable<CreatePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val fieldsBlank = request.title.isBlank() || request.body.isBlank() || request.userId.isBlank()

        if (fieldsBlank) {
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }

        val wasAcknowledged = postRepository.createPost(postRequest = request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Creating the post")
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}
fun Route.getAllPosts(
    postRepository: PostRepository
){
    get("getAllPosts"){
        val posts = postRepository.getPosts()
        call.respond(HttpStatusCode.OK,posts)
    }
}