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

    @Column(name = "permission_users", nullable = true)
    var permissionUsers: Int? = null,

    @Column(name = "permission_profile", nullable = true)
    var permissionProfile: Int? = null,

    @Column(name = "permission_roles", nullable = true)
    var permissionRoles: Int? = null,

    @Column(name = "permission_courses", nullable = true)
    var permissionCourses: Int? = null,

    @Column(name = "permission_groups", nullable = true)
    var permissionGroups: Int? = null,

    @Column(name = "permission_server_settings", nullable = true)
    var permissionServerSettings: Int? = null,

    @Column(name = "permission_announcements", nullable = true)
    var permissionAnnouncements: Int? = null

)