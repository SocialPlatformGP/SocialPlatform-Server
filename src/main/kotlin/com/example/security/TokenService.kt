package com.example.security

import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig

interface TokenService {
    fun generateToken(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String

}