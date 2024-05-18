package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity(name = "announcement_attachments")
@Table(name = "announcement_attachments")
class AnnouncementAttachmentMetaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "filename", nullable = false)
    var filename: String? = null,

    @Column(name = "size", nullable = false)
    var size: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var message: AnnouncementMessageEntity? = null

)