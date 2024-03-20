package com.example.routes

import com.example.data.models.MaterialFile
import com.example.data.models.MaterialFolder
import com.example.data.requests.CreateFileRequest
import com.example.data.requests.CreateFolderRequest
import com.example.data.requests.MaterialRequest
import com.example.data.responses.MaterialResponse
import com.example.repository.MaterialRepository
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import java.io.File
import java.util.*

fun Route.filesRoute(
    materialRepository: MaterialRepository
) {
    staticFiles("/", File("files"))

    post("uploadFolder") {
        val request = call.receiveNullable<CreateFolderRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val folder = MaterialFolder(
            name = request.name,
            path = request.path
        )
        val response = materialRepository.createMaterialFolder(folder)
        call.respond(response)
    }
    post("uploadFile") {
        val request = call.receiveNullable<CreateFileRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val folder = File("files/${request.path}")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = FileUtils.saveByteArrayToFile(request.content, "files/${request.path}/" + request.name)
        println(file.path)

        val response = materialRepository.createMaterialFile(
            MaterialFile(
                path = request.path,
                url = request.path + "/" + request.name,
                type = file.extension,
                name = request.name
            )
        )
        call.respond(response)

    }

    post("getFiles") {
        val request = call.receiveNullable<MaterialRequest>() ?: kotlin.run {
            println("****************** Can't receive the json")
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        println("****************** 00${request.path}***********************")
        val response = materialRepository.getMaterialResponse(request.path.trim())
        println("****************** $response***********************")
        call.respond(
            response
        )
    }
}
