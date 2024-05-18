package com.untis.service.impl

import com.untis.database.repository.CourseInstanceInfoRepository
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.UserRepository
import com.untis.model.CourseInstanceInfo
import com.untis.model.User
import com.untis.service.CourseInstanceInfoService
import com.untis.service.mapping.createCourseInstanceInfoEntity
import com.untis.service.mapping.createCourseInstanceInfoModel
import com.untis.service.mapping.createCourseModel
import com.untis.service.mapping.createUserModel
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
internal class DatabaseCourseInstanceInfoService(

    val instanceInfoRepository: CourseInstanceInfoRepository,

    val courseRepository: CourseRepository,

    val userRepository: UserRepository

) : CourseInstanceInfoService {


    override fun getAll() = instanceInfoRepository.findAll().map {
        createCourseInstanceInfoModel(it, createCourseModel(it.course!!))
    }.toSet()


    override fun getById(id: Long) = instanceInfoRepository.findById(id).get().let {
        createCourseInstanceInfoModel(it, createCourseModel(it.course!!))
    }

    override fun getByIdAndUser(id: Long, user: Long): CourseInstanceInfo? {
        throw NotImplementedError("Course instance infos are not bound to users")
    }

    override fun getAllForUser(user: Long): List<CourseInstanceInfo> {
        throw NotImplementedError("Course instance infos are not bound to users")
    }


    override fun existsById(id: Long) = instanceInfoRepository.existsById(id)


    override fun getByCourse(courseId: Long) = instanceInfoRepository
        .getAllForCourse(courseId)
        .map { createCourseInstanceInfoModel(it, createCourseModel(it.course!!)) }.toSet()


    override fun getByCourseDefinitive(
        courseId: Long,
        date: LocalDate,
        startTime: LocalTime,
    ) = instanceInfoRepository
        .getByDefinition(courseId, date, startTime)
        .map { createCourseInstanceInfoModel(it, createCourseModel(it.course!!)) }.firstOrNull()

    override fun update(
        instanceInfo: CourseInstanceInfo
    ): CourseInstanceInfo {
        if (!instanceInfoRepository.existsById(instanceInfo.id!!)) throw IllegalStateException("Can't find instance info with id '${instanceInfo.id}'")

        val course = instanceInfoRepository.findById(instanceInfo.id!!).get().course!!
        val changedLeaders = instanceInfoRepository.findById(instanceInfo.id!!).get().changedLeaders

        return createCourseInstanceInfoEntity(instanceInfo, course, changedLeaders).let(instanceInfoRepository::save)
            .let {
                createCourseInstanceInfoModel(
                    entity = it, course = createCourseModel(course)
                )
            }
    }

    override fun updateChangedLeaders(id: Long, changedLeaders: List<Long>?): CourseInstanceInfo {
        val entity = instanceInfoRepository.findById(id).get()
        val leaders = changedLeaders?.map(userRepository::findById)?.map { it.get() }

        entity.changedLeaders = leaders

        val course = createCourseModel(entity.course!!)

        return entity
            .let(instanceInfoRepository::save)
            .let { createCourseInstanceInfoModel(it, course) }
    }

    override fun changedLeadersFor(id: Long) = instanceInfoRepository
        .findById(id).get()
        .changedLeaders
        ?.map(::createUserModel)


    override fun create(model: CourseInstanceInfo): CourseInstanceInfo {
        val courseEntity = courseRepository.findById(model.course.id!!).get()
        val changedLeaders = model.changedLeaders?.map { userRepository.findById(it.id!!).get() }

        val entity = createCourseInstanceInfoEntity(model, courseEntity, changedLeaders)

        return entity.let(instanceInfoRepository::save).let { instanceInfo ->
            val courseModel = createCourseModel(courseEntity)
            createCourseInstanceInfoModel(instanceInfo, courseModel)
        }
    }


    override fun delete(id: Long): CourseInstanceInfo {
        val entity = instanceInfoRepository.findById(id).get()
        val courseEntity = entity.course!!

        instanceInfoRepository.delete(entity)

        return createCourseInstanceInfoModel(entity, createCourseModel(courseEntity))
    }
}