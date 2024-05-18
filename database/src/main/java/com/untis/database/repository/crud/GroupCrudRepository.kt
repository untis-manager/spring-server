package com.untis.database.repository.crud

import com.untis.database.entity.CourseEntity
import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface GroupCrudRepository : CrudRepository<GroupEntity, Long> {

    @Query(
        """
        SELECT u.groups
        FROM users u
        WHERE u.id = :userId
    """
    )
    fun getGroupsForUser(userId: Long): List<GroupEntity>

    @Query("""
        SELECT c
        FROM courses c
        INNER JOIN c.groups g
        WHERE g.id = :groupId
    """)
    fun getCoursesForGroup(groupId: Long): List<CourseEntity>

}