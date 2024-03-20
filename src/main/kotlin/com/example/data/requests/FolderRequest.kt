package com.example.data.requests

import com.example.data.models.MaterialFile
import com.example.utils.FileUtils
import kotlinx.serialization.Serializable
import java.io.File
import java.util.*

@Serializable
data class MaterialRequest(
    val path: String = "", // folder id or path to get all files and folders inside it
)
@Serializable
data class CreateFolderRequest (
    val name: String = "",
    val path: String = "",
){
    fun toMaterialFolder() = com.example.data.models.MaterialFolder(
        name = name,
        path = path,
    )
}
@Serializable
data class CreateFileRequest(
    val name: String = "",
    val type: String = "",
    val content: ByteArray = byteArrayOf(),
    val path: String = "",
){
    fun toMaterialFile() {
//        val folderId = UUID.randomUUID().toString()
//        val folder = File("files/${postId}")
//        if (!folder.exists()) {
//            folder.mkdirs()
//        }
//        val attachments = request.attachments.map {
//            val file = FileUtils.saveByteArrayToFile(it.file, "files/${postId}/" + it.name)
//            println(file.path)
//            it.copy(
//                file = byteArrayOf(),
//                url = postId+"/"+it.name,
//                type = it.type,
//                name = it.name
//            )
//        }
        MaterialFile(
            name = name,
            type = type,
            url = "",
            path = path,
        )
    }

}