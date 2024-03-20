package com.example.routes

import com.example.data.requests.SignUpRequest
import com.example.data.responses.AuthResponse
import com.example.data.models.User
import com.example.data.requests.CheckExistUserRequest
import com.example.data.requests.GetUserRequest
import com.example.data.requests.LoginRequest
import com.example.data.responses.IsEmailAvailableResponse
import com.example.data.responses.UserResponse
import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.h2.store.fs.FileUtils

fun Route.signUp(
    hashingService: HashingService,
    authRepository: AuthRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("isEmailAvailable") {
        val request = call.receiveNullable<CheckExistUserRequest>() ?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest,
                IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "Cannot parse request",
                )
            )
            return@post
        }
        val fieldsBlank = request.email.isBlank()
        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict,
                IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "Fields required",
                )
            )
            return@post
        }
        val user = authRepository.findUserByEmail(request.email)
        if (user != null) {
            call.respond(
                HttpStatusCode.OK,
                IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "User already exists with this email",
                )
            )
            return@post
        } else {
            call.respond(
                call.respond(
                    HttpStatusCode.OK,
                    IsEmailAvailableResponse(
                        isAvailable = true,
                        message = "User does not exist with this email and can be used to sign up",
                    )
                )

            )
        }
    }


    post("signup") {
        val request = call.receiveNullable<SignUpRequest>() ?: kotlin.run {
            println("Request is null")
            call.respond(
                HttpStatusCode.BadRequest,
                AuthResponse(
                    errorMessage = "Cannot parse request",
                    userId = "",
                    token = ""
                )
            )
            return@post
        }
        println(request)
        val fieldsBlank =
            request.phoneNumber.isBlank()
                    || request.firstName.isBlank()
                    || request.lastName.isBlank()
                    || request.bio.isBlank()
                    || request.birthdate == 0L
//                    || request.userProfilePictureURL.isBlank()

        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    errorMessage = "Fields required",
                    token = "",
                    userId = ""
                )
            )
            return@post
        }

        val saltedHash = hashingService.generateHash(request.password)
        val user = User(
            firstName = request.firstName,
            password = saltedHash.hash,
            email = request.email,
            salt = saltedHash.salt,
            birthdate = request.birthdate,
            bio = request.bio,
            profilePictureURL = request.profilePictureURL,
            phoneNumber = request.phoneNumber
        )

        val newUser = authRepository.createUser(user = user)
        if (newUser == null) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    errorMessage = "Server error, please try again later",
                    token = "",
                    userId = ""
                )
            )
            return@post
        } else {
            val token = tokenService.generateToken(
                config = tokenConfig,
                TokenClaim(name = "userId", value = newUser.id),
                TokenClaim(name = "email", value = newUser.email)
            )
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    errorMessage = "",
                    token = token,
                    userId = newUser.id
                )
            )
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
            call.respond(
                HttpStatusCode.BadRequest,
                AuthResponse(
                    errorMessage = "Cannot parse request",
                    token = "",
                    userId = ""
                )
            )
            return@post
        }
        val fieldsBlank = request.email.isBlank() || request.password.isBlank()

        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    errorMessage = "Fields required",
                    token = "",
                    userId = ""
                )
            )
            return@post
        }

        val user = authRepository.findUserByEmail(request.email)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    errorMessage = "User does not exist with this email",
                    token = "",
                    userId = ""
                )
            )
            return@post
        }

        val isValidPassword = hashingService.verify(
            plainText = request.password, saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )

        if (!isValidPassword) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    errorMessage = "Invalid password",
                    token = "",
                    userId = ""
                )
            )
            return@post
        }
        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim(name = "userId", value = user.id),
            TokenClaim(name = "email", value = user.email)
        )

        call.respond(
            status = HttpStatusCode.OK,
            AuthResponse(
                errorMessage = "",
                token = token,
                userId = user.id
            )
        )

    }


}

fun Route.getSignedUser(
    authRepository: AuthRepository,
) {
    get("getSignedUser") {
        val request = call.receiveNullable<GetUserRequest>() ?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest,
                UserResponse()
            )
            return@get
        }
        val fieldsBlank = request.id.isBlank()

        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict,
                UserResponse()
            )
            return@get
        }

        val user = authRepository.findUserById(request.id)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict,
                UserResponse()

            )
            return@get
        }

        call.respond(
            status = HttpStatusCode.OK,
            user.toResponse()
        )

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