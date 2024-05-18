package com.untis.security.auth

import com.untis.model.User
import com.untis.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtService @Autowired constructor(


    @Autowired
    private val userService: UserService,

    @Value("\${untis.auth.jwt.expiration}")
    private val expiration: String,

    @Value("\${untis.auth.jwt.secret}")
    private val secret: String

) {


    fun createForUser(user: User): String {
        val claims = user.createClaims()

        val key = Keys.hmacShaKeyFor(secret.toByteArray())

        return Jwts
            .builder()
            .setClaims(claims)
            .setExpiration(Date.from(Instant.now().plusSeconds(expiration.toLong())))
            .signWith(key)
            .compact()
    }

    fun getTokenFromHeader(req: HttpServletRequest): String? {
        val header: String? = req.getHeader(AuthHeader)
        return header?.getTokenFromHeader()
    }

    fun getClaimsFromToken(token: String): Claims? {
        return try {
            Jwts
                .parserBuilder()
                .setSigningKey(secret.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }

    fun validate(req: HttpServletRequest): User? {
        val token = getTokenFromHeader(req) ?: return null
        val claims = getClaimsFromToken(token)?.ensureEmail() ?: return null

        val email = claims.subject
        return userService.getByEmail(email)
    }

    private fun String.getTokenFromHeader(): String? = if (startsWith(Bearer))
        removePrefix(Bearer)
    else
        null

    private fun Claims.ensureEmail(): Claims? {
        return if (this.subject == null) null
        else this
    }

    private fun User.createClaims(): Claims = Jwts
        .claims().apply {
            expiration = Date.from(Instant.now().plusSeconds(this@JwtService.expiration.toLong()))
            issuedAt = Date.from(Instant.now())
            subject = email
        }

    @Suppress("ConstPropertyName")
    companion object {

        const val AuthHeader = "Authorization"

        const val Bearer = "Bearer "

    }

}