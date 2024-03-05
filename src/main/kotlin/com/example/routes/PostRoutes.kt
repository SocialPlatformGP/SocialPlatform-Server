package com.example.routes

import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
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
        val request = call.receiveNullable<PostResponse>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val fieldsBlank = request.title.isBlank() || request.body.isBlank()

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
fun Route.upVotePost(
    postRepository: PostRepository
){
    post("upVotePost"){
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,"مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.upvotePost(request)
        call.respond(HttpStatusCode.OK,wasAcknowledged)

    }
}
fun Route.downVotePost(
    postRepository: PostRepository
){
    post("downVotePost"){
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,"مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.downvotePost(request)
        call.respond(HttpStatusCode.OK,wasAcknowledged)
    }
}
fun Route.deletePost(
    postRepository: PostRepository
){
    delete ("deletePost"){
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,"مش عارف استقبل الjson")
            return@delete
        }
        val wasAcknowledged = postRepository.deletePost(request)
        call.respond(HttpStatusCode.OK,wasAcknowledged)
    }
}
fun Route.updatePost(
    postRepository: PostRepository
){
    put("updatePost"){
        val request = call.receiveNullable<PostResponse>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,"مش عارف استقبل الjson")
            return@put
        }
        val wasAcknowledged = postRepository.updatePost(request)
        call.respond(HttpStatusCode.OK,wasAcknowledged)
    }
}
