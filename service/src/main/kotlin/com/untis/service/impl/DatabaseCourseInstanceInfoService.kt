package com.untis.service.impl

import com.untis.database.repository.CourseInstanceInfoRepository
import com.untis.database.repository.CourseRepository
import com.untis.database.repository.UserRepository
import com.untis.model.CourseInstanceInfo
import com.untis.service.CourseInstanceInfoService
import com.untis.service.GroupService
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

    val userRepository: UserRepository,

    val groupService: GroupService

) : CourseInstanceInfoService {


    override fun getAll() = instanceInfoRepository.findAll().map {
        createCourseInstanceInfoModel(
            entity = it,
            course = createCourseModel(it.course!!),
            changedLeaders = if (it.isLeaderChange!!) {
                it.changedLeaders!!.map { user ->
                    val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                    createUserModel(user, perms)
                }
            } else null
        )
    }.toSet()


    override fun getById(id: Long) = instanceInfoRepository.findById(id).get().let {
        createCourseInstanceInfoModel(
            entity = it,
            course = createCourseModel(it.course!!),
            changedLeaders = if (it.isLeaderChange!!) {
                it.changedLeaders!!.map { user ->
                    val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                    createUserModel(user, perms)
                }
            } else null
        )
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
        .map {
            createCourseInstanceInfoModel(
                entity = it,
                course = createCourseModel(it.course!!),
                changedLeaders = if (it.isLeaderChange!!) {
                    it.changedLeaders!!.map { user ->
                        val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                        createUserModel(user, perms)
                    }
                } else null
            )
        }.toSet()


    override fun getByCourseDefinitive(
        courseId: Long,
        date: LocalDate,
        startTime: LocalTime,
    ) = instanceInfoRepository
        .getByDefinition(courseId, date, startTime)
        .map {
            createCourseInstanceInfoModel(
                entity = it,
                course = createCourseModel(it.course!!),
                changedLeaders = if (it.isLeaderChange!!) {
                    it.changedLeaders!!.map { user ->
                        val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                        createUserModel(user, perms)
                    }
                } else null
            )
        }.firstOrNull()

    override fun update(
        instanceInfo: CourseInstanceInfo
    ): CourseInstanceInfo {
        if (!instanceInfoRepository.existsById(instanceInfo.id!!)) throw IllegalStateException("Can't find instance info with id '${instanceInfo.id}'")

        val course = instanceInfoRepository.findById(instanceInfo.id!!).get().course!!
        val changedLeaders = instanceInfoRepository.findById(instanceInfo.id!!).get().changedLeaders

        return createCourseInstanceInfoEntity(instanceInfo, course, changedLeaders).let(instanceInfoRepository::save)
            .let {
                createCourseInstanceInfoModel(
                    entity = it,
                    course = createCourseModel(it.course!!),
                    changedLeaders = if (it.isLeaderChange!!) {
                        it.changedLeaders!!.map { user ->
                            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                            createUserModel(user, perms)
                        }
                    } else null
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
            .let {
                createCourseInstanceInfoModel(
                    entity = it,
                    course = course,
                    changedLeaders = if (it.isLeaderChange!!) {
                        it.changedLeaders!!.map { user ->
                            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                            createUserModel(user, perms)
                        }
                    } else null
                )
            }
    }

    override fun changedLeadersFor(id: Long) = instanceInfoRepository
        .findById(id).get()
        .changedLeaders
        ?.map { user ->
            val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

            createUserModel(user, perms)
        }


    override fun create(model: CourseInstanceInfo): CourseInstanceInfo {
        val courseEntity = courseRepository.findById(model.course.id!!).get()
        val changedLeaders = model.changedLeaders?.map { userRepository.findById(it.id!!).get() }

        val entity = createCourseInstanceInfoEntity(model, courseEntity, changedLeaders)

        return entity.let(instanceInfoRepository::save).let { instanceInfo ->
            val courseModel = createCourseModel(courseEntity)
            createCourseInstanceInfoModel(
                entity = instanceInfo,
                course = courseModel,
                changedLeaders = if (instanceInfo.isLeaderChange!!) {
                    instanceInfo.changedLeaders!!.map { user ->
                        val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                        createUserModel(user, perms)
                    }
                } else null
            )
        }
    }


    override fun delete(id: Long): CourseInstanceInfo {
        val entity = instanceInfoRepository.findById(id).get()
        val courseEntity = entity.course!!

        instanceInfoRepository.delete(entity)

        return createCourseInstanceInfoModel(
            entity = entity,
            course = createCourseModel(courseEntity),
            changedLeaders = if (entity.isLeaderChange!!) {
                entity.changedLeaders!!.map { user ->
                    val perms = groupService.getMergedPermissions(user.groups!!.map { it.id!! })

                    createUserModel(user, perms)
                }
            } else null
        )
    }
}