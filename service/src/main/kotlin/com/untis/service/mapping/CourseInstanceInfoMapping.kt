package com.untis.service.mapping

import com.untis.database.entity.CourseEntity
import com.untis.database.entity.CourseInstanceInfoEntity
import com.untis.database.entity.UserEntity
import com.untis.model.Course
import com.untis.model.CourseInstanceInfo
import com.untis.model.User

internal fun createCourseInstanceInfoModel(
    entity: CourseInstanceInfoEntity,
    course: Course,
    changedLeaders: List<User>?
) =
    CourseInstanceInfo(
        id = entity.id,
        notes = entity.notes!!,
        date = entity.date!!,
        startTime = entity.startTime!!,
        course = course,
        isCancelled = entity.isCanceled!!,
        changedLocation = entity.changedLocation,
        changedLeaders = changedLeaders
    )

internal fun createCourseInstanceInfoEntity(
    model: CourseInstanceInfo,
    course: CourseEntity,
    changedLeaders: List<UserEntity>?
) =
    CourseInstanceInfoEntity(
        id = model.id,
        notes = model.notes,
        date = model.date,
        startTime = model.startTime,
        course = course,
        isCanceled = model.isCancelled,
        isLocationChange = model.changedLocation != null,
        changedLocation = model.changedLocation,
        isLeaderChange = changedLeaders != null,
        changedLeaders = changedLeaders
    )