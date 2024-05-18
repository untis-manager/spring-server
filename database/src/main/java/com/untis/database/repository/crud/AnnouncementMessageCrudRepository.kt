package com.untis.database.repository.crud

import com.untis.database.entity.AnnouncementMessageEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
internal interface AnnouncementMessageCrudRepository : CrudRepository<AnnouncementMessageEntity, Long> {

    @Query("""
        SELECT m
        FROM announcement_messages m
        INNER JOIN m.recipients r
        WHERE r.id in :groupIds
    """)
    fun getAllForGroups(groupIds: List<Long>): List<AnnouncementMessageEntity>

    @Query("""
        SELECT m
        FROM announcement_messages m
        INNER JOIN announcement_attachments a on a.message.id = m.id
        WHERE a.id = :attachmentId
    """)
    fun getForAttachment(attachmentId: Long): Optional<AnnouncementMessageEntity>

    @Query("""
        SELECT DISTINCT message
        FROM announcement_messages message
        INNER JOIN message.recipients group
        WHERE group in (
            SELECT u.groups FROM users u WHERE u.id = :userId
        )
    """)
    fun getAllForUser(userId: Long): List<AnnouncementMessageEntity>

}