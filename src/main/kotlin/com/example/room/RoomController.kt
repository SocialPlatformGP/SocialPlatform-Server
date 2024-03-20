package com.example.room

import com.example.data.models.Message
import com.example.repository.MessageDataSource
import com.example.repository.MessageDataSourceImpl
import com.example.repository.PostRepository
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw MemberAlreadyExistsException()
        }
        members[username] = Member(username, sessionId, socket)
    }

    suspend fun sendMessage(senderUserName: String, message: String) {
        members.values.forEach { member: Member ->
            val messageEntity = Message(
                message,
                senderUserName,
                System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntity)
            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }
    suspend fun getMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }
    suspend fun removeMember(username: String) {
        members[username]?.let { member ->
            member.socket.close()
            members.remove(username)
        }
    }

}