package com.untis.security.auth

import com.untis.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UntisUserDetailsService @Autowired constructor(

    private val userService: UserService

) : UserDetailsService {

    override fun loadUserByUsername(username: String?) =
        username?.let(userService::getByEmail) ?: throw UsernameNotFoundException("User with email $username not found")

}