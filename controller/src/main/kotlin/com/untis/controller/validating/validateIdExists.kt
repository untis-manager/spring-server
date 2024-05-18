package com.untis.controller.validating

import com.untis.controller.base.ControllerScope
import com.untis.model.exception.RequestException
import com.untis.service.base.BaseService
import java.util.*

/**
 * Validates that a user with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateUserExists(id: Long) = validate(id, null, "user", userService)

/**
 * Validates that a course with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateCourseExists(id: Long, userId: Long? = null) = validate(id, userId, "course", courseService)

/**
 * Validates that a course instance info with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateCourseInstanceInfoExists(id: Long) =
    validate(id, null, "course-instance-info", courseInstanceInfoService)

/**
 * Validates that a course timing with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateCourseTimingExists(id: Long) = validate(id, null, "course-timing", courseTimingService)

/**
 * Validates that a group with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateGroupExists(id: Long, userId: Long? = null) = validate(id, null, "group", groupService)

/**
 * Validates that a role with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateRoleExists(id: Long) = validate(id, null, "role", roleService)

/**
 * Validates that an announcement message with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateAnnouncementMessageExists(id: Long, userId: Long? = null) =
    validate(id, userId, "announcement message", announcementMessageService)

/**
 * Validates that an announcement attachment with [id] exists
 *
 * @param id The id to check for
 */
fun ControllerScope.validateAnnouncementAttachmentExists(id: Long) =
    validate(id, null, "announcement attachment", announcementAttachmentService)

fun ControllerScope.validateSignUpTokenExists(token: UUID) {
    if (signUpTokenService == null) throw IllegalStateException("Can't check for sign-up-token without the service")

    if (signUpTokenService!!.getForToken(token) == null) throw RequestException.IdNotFound(
        token.toString(),
        "sign-up-token"
    )
}

/**
 * Validates that a Security Token with a specific token exists
 *
 * @param token The token
 */
fun ControllerScope.validateTokenExists(token: UUID, userId: Long? = null) {
    if (tokenService == null) throw IllegalStateException("Can't check for token token without the service")

    if (userId == null) {
        if (!tokenService!!.existsByToken(token)) throw RequestException.IdNotFound(token.toString(), "token")
    } else {
        if (tokenService!!.getByTokenAndUser(token, userId) == null) throw RequestException.IdNotFound(
            token.toString(),
            "token"
        )
    }
}


@Suppress("SameParameterValue")
private fun <Model> validate(id: Long, userId: Long?, type: String, service: BaseService<Model>?) {
    if (service == null) throw IllegalStateException("Can't check for $type id without the service")

    if (userId == null) {
        if (!service.existsById(id)) throw RequestException.IdNotFound(id.toString(), type)
    } else {
        if (service.getByIdAndUser(id, userId) == null) throw RequestException.IdNotFound(id.toString(), type)
    }

}