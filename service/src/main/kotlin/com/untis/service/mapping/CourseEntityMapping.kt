package com.untis.service.mapping

import com.untis.database.entity.CourseEntity
import com.untis.database.entity.CourseTimingEntity
import com.untis.database.entity.GroupEntity
import com.untis.database.entity.UserEntity
import com.untis.model.Course

internal fun createCourseModel(entity: CourseEntity) =
    Course(
        id = entity.id!!,
        name = entity.name!!,
        description = entity.description!!
    )

internal fun createCourseEntity(model: Course, leaders: Set<UserEntity>, groups: Set<GroupEntity>, timings: Set<CourseTimingEntity>) = CourseEntity(
    id = model.id,
    name = model.name,
    description = model.description,
    timings = timings,
    leaders = leaders,
    groups = groups.toMutableSet()
)