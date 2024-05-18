package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "security_token")
@Table(name = "security_token")
class SecurityTokenEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = 0,

    @Column(name = "token", nullable = false)
    var token: UUID? = null,

    @Column(name = "type", nullable = false)
    var type: String? = null,

    @Column(name = "expiration_date", nullable = false)
    var expirationDate: LocalDateTime? = null,

    @Column(name = "used", nullable = false)
    var used: Boolean? = null,

    @Column(name = "additional_info", nullable = true)
    var additionalInfo: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: UserEntity? = null


)