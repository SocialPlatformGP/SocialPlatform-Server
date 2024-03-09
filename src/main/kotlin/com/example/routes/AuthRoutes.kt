package com.example.routes

import com.example.data.requests.SignUpRequest
import com.example.data.responses.AuthResponse
import com.example.data.models.User
import com.example.data.requests.CheckExistUserRequest
import com.example.data.requests.LoginRequest
import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    authRepository: AuthRepository
) {
    post("checkExistUser"){
        val request = call.receiveNullable<CheckExistUserRequest>()?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val fieldsBlank = request.email.isBlank() || request.password.isBlank()
        val tooShortPassword = request.password.length < 6
        if(fieldsBlank){
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }
        if (tooShortPassword) {
            call.respond(HttpStatusCode.Conflict, message = "Password too short")
            return@post
        }
        if(authRepository.findUserByEmail(request.email) != null){
            call.respond(HttpStatusCode.Conflict, message = "User already exists")
            return@post
        }else{
            call.respond(HttpStatusCode.OK, message = "User does not exist")
        }
    }


    post("signup") {
        val request = call.receiveNullable<SignUpRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val fieldsBlank =
            request.userPhoneNumber.isBlank()
                    || request.userFirstName.isBlank()
                    || request.userLastName.isBlank()
                    || request.userBio.isBlank()
                    || request.userBirthdate.isBlank()
                    || request.userProfilePictureURL.isBlank()

        if (fieldsBlank) {
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }

        val saltedHash = hashingService.generateHash(request.userPassword)
        val user = User(
            userFirstName = request.userFirstName,
            userPassword = saltedHash.hash,
            userEmail = request.userEmail,
            salt = saltedHash.salt,
            userBirthdate = request.userBirthdate,
            userBio = request.userBio,
            userProfilePictureURL = request.userProfilePictureURL,
            userPhoneNumber = request.userPhoneNumber
        )

        val wasAcknowledged = authRepository.createUser(user = user)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Creating the user")
            return@post
        }else{
            call.respond(HttpStatusCode.OK)
        }



    }
}


fun Route.signIn(
    authRepository: AuthRepository,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val fieldsBlank = request.email.isBlank() || request.password.isBlank()

        if (fieldsBlank) {
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }

        val user = authRepository.findUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, message = "Email does not exist")
            return@post
        }

        val isValidPassword = hashingService.verify(
            plainText = request.password, saltedHash = SaltedHash(
                hash = user.userPassword,
                salt = user.salt
            )
        )

        if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict, message = "Incorrect  Password")
            return@post
        }
        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim(name = "userId", value = user.id),
            TokenClaim(name = "email", value = user.userEmail)
        )

        call.respond(status = HttpStatusCode.OK, message = AuthResponse(token = token))

    }


}


fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}


fun Route.home() {
    authenticate {
        get("home") {
            val principal = call.principal<JWTPrincipal>()
            val id = principal?.payload?.getClaim("userId").toString()
            val email = principal?.payload?.getClaim("email").toString()
            call.respond(HttpStatusCode.OK, message = "Your UserId is $id and your email is $email")
        }
    }
}