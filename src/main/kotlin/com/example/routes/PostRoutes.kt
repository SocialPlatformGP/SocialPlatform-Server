package com.example.routes

import com.example.data.requests.AddTagRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import com.example.repository.PostRepository
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.io.File
import java.util.UUID

private val BASE_URL = "http://192.168.1.4:8080/"
fun Route.createPost(
    postRepository: PostRepository
) {
    post("createPost") {
        val request = call.receiveNullable<PostResponse>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val fieldsBlank = request.title.isBlank() || request.body.isBlank()

        if (fieldsBlank) {
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }

        if (request.attachments.isEmpty()) {
            val wasAcknowledged = postRepository.createPost(postRequest = request)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, message = "Error Creating the post")
                return@post
            }
            call.respond(HttpStatusCode.OK, message = "Post created successfully")
            return@post
        } else {
            val postId = UUID.randomUUID().toString()
            val folder = File("files/${postId}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val attachments = request.attachments.map {
                val file = FileUtils.saveByteArrayToFile(it.file, "files/${postId}/" + it.name)
                println(file.path)
                it.copy(
                    file = byteArrayOf(),
                    url = postId+"/"+it.name,
                    type = it.type,
                    name = it.name
                )
            }
            val wasAcknowledged = postRepository.createPost(postRequest = request.copy(attachments = attachments))
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, message = "Error Creating the post")
                return@post
            }
            call.respond(HttpStatusCode.OK, message = "Post created successfully")
        }
    }
    post("insertTag") {
        val request = call.receiveNullable<AddTagRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.insertTag(request.toEntity())
        call.respond(HttpStatusCode.OK, wasAcknowledged)

    }
}

fun Route.getAllPosts(
    postRepository: PostRepository
) {
    get("getAllPosts") {
        val posts = postRepository.getPosts()
        call.respond(HttpStatusCode.OK, posts)
    }
    get("getAllTags") {
        val response = postRepository.getTags()
        call.respond(HttpStatusCode.OK, response)

    }
}

fun Route.upVotePost(
    postRepository: PostRepository
) {
    post("upVotePost") {
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.upvotePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)

    }
}

fun Route.downVotePost(
    postRepository: PostRepository
) {
    post("downVotePost") {
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.downvotePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)
    }
}

fun Route.deletePost(
    postRepository: PostRepository
) {
    delete("deletePost") {
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@delete
        }
        val wasAcknowledged = postRepository.deletePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)
    }
}

fun Route.updatePost(
    postRepository: PostRepository
) {
    put("updatePost") {
        val request = call.receiveNullable<PostResponse>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@put
        }
        val wasAcknowledged = postRepository.updatePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)
    }
    post("getNewPosts") {
        val request = call.receiveNullable<LastUpdated>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val response = postRepository.getNewPosts(request.lastUpdated)
        println(request.lastUpdated.toString() + "$response")
        call.respond(HttpStatusCode.OK, response)

    }
}
@Serializable
data class LastUpdated(val lastUpdated :LocalDateTime)

