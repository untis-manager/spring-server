package com.untis.database.entity

import jakarta.persistence.*

@Entity(name = "server_settings")
@Table(name = "server_settings")
class ServerSettingsEntity(

    @Id
    val id: Long = 1,

    @Column(name = "signup_mode", nullable = false)
    var signupMode: String? = null,

    @Column(name = "signup_free_default_role_id", nullable = true)
    var signupFreeDefaultRoleId: Long? = null,

    @Column(name = "signup_free_needs_email_verification", nullable = true)
    var signupFreeNeedsEmailVerification: Boolean? = null


)