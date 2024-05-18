package com.untis.controller.base

import com.untis.service.*

/**
 * Base class for a controller that may have access to services
 */
abstract class ControllerScope(

    /**
     * The user service
     */
    open val userService: UserService? = null,

    /**
     * The course repository
     */
    open val courseService: CourseService? = null,

    /**
     * The course timing service
     */
    open val courseTimingService: CourseTimingService? = null,

    /**
     * The group service
     */
    open val groupService: GroupService? = null,

    /**
     * The token service
     */
    open val tokenService: SecurityTokenService? = null,

    /**
     * The role service
     */
    open val roleService: RoleService? = null,

    /**
     * The service for sign-up-tokens
     */
    open val signUpTokenService: SignUpTokenService? = null,

    /**
     * The instance info service
     */
    open val courseInstanceInfoService: CourseInstanceInfoService? = null,

    /**
     * The announcement message service
     */
    open val announcementMessageService: AnnouncementMessageService? = null,

    /**
     * The announcement attachment service
     */
    open val announcementAttachmentService: AnnouncementAttachmentService? = null,

)