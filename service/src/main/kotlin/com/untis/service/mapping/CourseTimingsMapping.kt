package com.untis.service.mapping

import com.untis.database.entity.CourseTimingEntity
import com.untis.model.CourseTiming
import com.untis.model.RepeatingCourseTimingLimit

internal fun createCourseTimingModel(entity: CourseTimingEntity) =
    if (entity.isSingle)
        CourseTiming.Single(
            date = entity.singleDate!!,
            start = entity.startTime!!,
            end = entity.endTime!!,
            breakTime = entity.breakTime!!,
            name = entity.name!!,
            id = entity.id!!,
            location = entity.location!!
        )
    else CourseTiming.Repeating(
        firstDate = entity.repeatingStartDate!!,
        dayInterval = entity.repeatingInterval!!,
        limit = entity.extractCourseTimingLimit(),
        start = entity.startTime!!,
        end = entity.endTime!!,
        breakTime = entity.breakTime!!,
        name = entity.name!!,
        id = entity.id!!,
        location = entity.location!!
    )

internal fun createCourseTimingEntity(model: CourseTiming) = CourseTimingEntity(
    id = model.id,
    name = model.name,
    startTime = model.start,
    endTime = model.end,
    breakTime = model.breakTime,
    isRepeating = model is CourseTiming.Repeating,
    isSingle = model is CourseTiming.Single,
    repeatingStartDate = (model as? CourseTiming.Repeating)?.firstDate,
    repeatingInterval = (model as? CourseTiming.Repeating)?.dayInterval,
    repeatingEndDate = ((model as? CourseTiming.Repeating)?.limit as? RepeatingCourseTimingLimit.Absolute)?.end,
    repeatingRepetitions = ((model as? CourseTiming.Repeating)?.limit as? RepeatingCourseTimingLimit.RepeatingLimit)?.repetitions,
    singleDate = (model as? CourseTiming.Single)?.date,
    location = model.location
)

internal fun CourseTimingEntity.extractCourseTimingLimit() =
    if (this.repeatingRepetitions != null)
        RepeatingCourseTimingLimit.RepeatingLimit(repeatingRepetitions!!)
    else RepeatingCourseTimingLimit.Absolute(this.repeatingEndDate!!)
