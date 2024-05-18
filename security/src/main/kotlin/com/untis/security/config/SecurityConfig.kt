package com.untis.security.config

import com.untis.model.Permission
import com.untis.security.auth.JwtFilter
import com.untis.security.auth.UntisUserDetailsService
import com.untis.security.config.authority.*
import com.untis.security.config.authority.courses
import com.untis.security.config.authority.groups
import com.untis.security.config.authority.profile
import com.untis.security.config.authority.role
import com.untis.security.config.authority.serverSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class SecurityConfig @Autowired constructor(

    private val userDetailsService: UntisUserDetailsService,

    private val jwtFilter: JwtFilter

) {

    @Bean
    fun authenticationManager(http: HttpSecurity, passwordEncoder: PasswordEncoder): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(security: HttpSecurity): SecurityFilterChain = security
        .authorizeHttpRequests { configurer ->
            configurer
                .requestMatchers("/test/**").permitAll()
                .configureAuth()
                .configureServerSettings()
                .configureUser()

        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .exceptionHandling {
            it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        }
        .csrf {
            it.disable()
        }
        .cors { }
        .userDetailsService(userDetailsService)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(8)

}

private typealias Configurer = AuthorizeHttpRequestsConfigurer<*>.AuthorizationManagerRequestMatcherRegistry

private fun Configurer.configureAuth(): Configurer =
    requestMatchers("/auth/validate/**").permitAll()
        .requestMatchers("/auth/signup/**").permitAll()
        .requestMatchers("/auth/login/**").permitAll()
        .requestMatchers("/auth/refresh/**").permitAll()
        .requestMatchers("/auth/logout/**").authenticated()

private fun Configurer.configureServerSettings(): Configurer =
    requestMatchers(HttpMethod.GET, "/server-config/signup-mode/").permitAll()
        .requestMatchers(HttpMethod.POST, "/server-config/signup-mode/")
        .hasAnyAuthority(*serverSettings(Permission.Simple.Write))


private fun Configurer.configureUser(): Configurer =
    requestMatchers(HttpMethod.PATCH, "/user/").hasAnyAuthority(*profile(Permission.Profile.Edit))
        .requestMatchers(HttpMethod.DELETE, "/user/").hasAnyAuthority(*profile(Permission.Profile.Edit))
        .requestMatchers(HttpMethod.GET, "/user/").hasAnyAuthority(*profile(Permission.Profile.Read))
        .requestMatchers(HttpMethod.PATCH, "/user/email/").hasAnyAuthority(*profile(Permission.Profile.Edit))
        .requestMatchers(HttpMethod.PATCH, "/user/password/").hasAnyAuthority(*profile(Permission.Profile.Edit))
        .requestMatchers(HttpMethod.GET, "/user/role/").hasAnyAuthority(*role(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/courses/").hasAnyAuthority(*courses(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/courses/{id}/").hasAnyAuthority(*courses(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/courses/{id}/groups/").hasAnyAuthority(*groups(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/courses/{id}/leaders/").permitAll() // Handled in controller
        .requestMatchers(HttpMethod.GET, "/user/groups/").hasAnyAuthority(*groups(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/groups/{id}/").hasAnyAuthority(*groups(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/groups/{id}/courses/").hasAnyAuthority(*courses(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/groups/{id}/users/").permitAll() // Handled in controller
        .requestMatchers(HttpMethod.GET, "/user/announcements/").hasAnyAuthority(*announcements(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/announcements/{id}/").hasAnyAuthority(*announcements(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/announcements/{id}/groups/").hasAnyAuthority(*groups(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/announcements/{id}/attachments/").hasAnyAuthority(*announcements(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/announcements/{messageId}/attachments/{attachmentId}/").hasAnyAuthority(*announcements(Permission.Scoped.Own))
        .requestMatchers(HttpMethod.GET, "/user/announcements/{messageId}/attachments/{attachmentId}/file/").hasAnyAuthority(*announcements(Permission.Scoped.Own))






























