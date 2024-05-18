package com.untis.service.impl

import com.untis.database.repository.CourseTimingRepository
import com.untis.model.CourseTiming
import com.untis.service.CourseTimingService
import com.untis.service.mapping.createCourseTimingEntity
import com.untis.service.mapping.createCourseTimingModel
import org.hibernate.resource.beans.container.internal.NotYetReadyException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseCourseTimingService @Autowired constructor(

    private val courseTimingRepository: CourseTimingRepository

) : CourseTimingService {

    override fun update(
        timing: CourseTiming,
    ): CourseTiming {
        if (!courseTimingRepository.existsById(timing.id!!)) throw IllegalStateException("Can't find course timing with id '${timing.id}'")

        return createCourseTimingEntity(timing)
            .let(courseTimingRepository::save)
            .let(::createCourseTimingModel)
    }

    override fun getAll(): Set<CourseTiming> = courseTimingRepository
        .findAll().map(::createCourseTimingModel).toSet()

    override fun getById(id: Long) = courseTimingRepository
        .findById(id).get()
        .let(::createCourseTimingModel)

    override fun getByIdAndUser(id: Long, user: Long): CourseTiming? {
        throw NotImplementedError("Course timings are not bound to users")
    }

    override fun getAllForUser(user: Long): List<CourseTiming> {
        throw NotImplementedError("Course timings are not bound to users")
    }

    override fun create(model: CourseTiming) =
        model.let(::createCourseTimingEntity)
            .let(courseTimingRepository::save)
            .let(::createCourseTimingModel)

    override fun delete(id: Long) = courseTimingRepository
        .findById(id).get()
        .apply(courseTimingRepository::delete)
        .let(::createCourseTimingModel)

    override fun existsById(id: Long) = courseTimingRepository
        .existsById(id)
}