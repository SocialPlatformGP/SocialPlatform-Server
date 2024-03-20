package com.example.routes

import com.example.plugins.ChatSession
import com.example.room.MemberAlreadyExistsException
import com.example.room.RoomController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.net.http.WebSocket

fun Route.chatRoute(
    roomController: RoomController
) {
    webSocket("/chat-socket"){
        val session = call.sessions.get<ChatSession>()
        if(session == null){
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
            roomController.onJoin(
                session.userName,
                session.sessionId,
                this
            )
            incoming.consumeEach {frame->
                if(frame is Frame.Text){
                    val receivedText = frame.readText()
                    roomController.sendMessage(session.userName, receivedText)
                }

            }
        }catch (e:MemberAlreadyExistsException){
            call.respond(HttpStatusCode.Conflict)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            roomController.removeMember(session.userName)
        }

    }
}
fun Route.getAllMessages(
    roomController: RoomController
){
    get("/messages"){
        val messages = roomController.getMessages()
        call.respond(HttpStatusCode.OK,messages)
    }
}