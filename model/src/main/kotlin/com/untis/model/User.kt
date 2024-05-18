package com.untis.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

/**
 * Represents a user in the system
 */
data class User(

    /**
     * The id of the user
     * Null if it has not yet been saved
     */
    val id: Long?,

    /**
     * Address information of the user
     */
    val addressInfo: AddressInfo,

    /**
     * First name of the user
     */
    val firstName: String,

    /**
     * Second name of the user
     */
    val lastName: String,

    /**
     * E-mail of the user
     */
    val email: String,

    val encodedPassword: String,

    /**
     * Birthday of the user
     */
    val birthDate: LocalDate,

    /**
     * Info about the gender of the user
     */
    val gender: GenderInfo,

    /**
     * The permissions that the user is given by the groups it is part of.
     */
    val permissions: PermissionsBundle

) : UserDetails {

    override fun getAuthorities() = permissions.allWithPrefix().map { entry ->
        entry.value.authoritiesDownwards(entry.key)
    }.flatten()
        .map(::SimpleGrantedAuthority)

    override fun getPassword() = encodedPassword

    override fun getUsername() = this.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}