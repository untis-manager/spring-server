package com.untis.database.entity

import jakarta.persistence.*

/**
 * A role inside the whole system, applied to a specific user.
 * States the role of the user compared to others and is used for authentication and allowed actions.
 */
@Entity(name = "roles")
@Table(name = "roles")
class RoleEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

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