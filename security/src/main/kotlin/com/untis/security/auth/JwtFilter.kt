package com.untis.security.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter @Autowired constructor(

    private val jwtService: JwtService

) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val user = jwtService.validate(request)

        if (user == null) {
            filterChain.doFilter(request, response)
            return
        }

        val authToken = UsernamePasswordAuthenticationToken(user, user.encodedPassword, user.authorities)
        authToken.details = WebAuthenticationDetails(request)
        SecurityContextHolder.getContext().authentication = authToken

        filterChain.doFilter(request, response)
    }

}