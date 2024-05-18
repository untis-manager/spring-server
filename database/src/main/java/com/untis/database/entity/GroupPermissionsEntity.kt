package com.untis.database.entity

import jakarta.persistence.*

/**
 * A record that describes the authorities that a certain group is given.
 */
@Entity(name = "permissions")
@Table(name = "permissions")
class GroupPermissionsEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    /*
    Permissions
     */

    @Column(name = "permission_users", nullable = false)
    var permissionUsers: Int? = null,

    @Column(name = "permission_profile", nullable = false)
    var permissionProfile: Int? = null,

    @Column(name = "permission_roles", nullable = false)
    var permissionRoles: Int? = null,

    @Column(name = "permission_courses", nullable = false)
    var permissionCourses: Int? = null,

    @Column(name = "permission_groups", nullable = false)
    var permissionGroups: Int? = null,

    @Column(name = "permission_server_settings", nullable = false)
    var permissionServerSettings: Int? = null,

    @Column(name = "permission_announcements", nullable = false)
    var permissionAnnouncements: Int? = null

)