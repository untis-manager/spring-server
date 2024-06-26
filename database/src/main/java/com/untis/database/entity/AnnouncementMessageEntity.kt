package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

/**
 * Represents one announcement message in the database
 */
@Entity(name = "announcement_messages")
@Table(name = "announcement_messages")
class AnnouncementMessageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String? = null,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String? = null,

    @Column(name = "date_sent", nullable = false)
    var dateSent: LocalDateTime? = null,

    @Column(name = "needs_confirmation", nullable = false)
    var needsConfirmation: Boolean? = null,

    // TODO: Should be 'group_id' not 'user_id'
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "announcement_recipients",
        joinColumns = [JoinColumn(name = "announcement_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var recipients: List<GroupEntity>? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "announcement_confirmed",
        joinColumns = [JoinColumn(name = "announcement_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var confirmedBy: List<UserEntity>? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "announcement_read",
        joinColumns = [JoinColumn(name = "announcement_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var readBy: List<UserEntity>? = null,

    //TODO: Should be 'user_id' not 'message_id'
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var author: UserEntity? = null

)