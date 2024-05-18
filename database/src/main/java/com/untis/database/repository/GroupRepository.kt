package com.untis.database.repository

import com.untis.database.entity.CourseEntity
import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import com.untis.database.repository.base.SimpleRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Repository to access the entries of the [GroupEntity] table
 */
@Repository
interface GroupRepository : SimpleRepository<GroupEntity> {

    fun getGroupsForUser(userId: Long): List<GroupEntity>

    fun getCoursesForGroup(groupId: Long): List<CourseEntity>

}